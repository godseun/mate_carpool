package com.mate.carpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mate.carpool.dto.ResponseDTO;
import com.mate.carpool.dto.UserDTO;
import com.mate.carpool.model.UserEntity;
import com.mate.carpool.security.TokenProvider;
import com.mate.carpool.service.OAuthService;
import com.mate.carpool.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "oauth", description = "소셜 로그인 API")
@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

  @Autowired
  private OAuthService oAuthService;

  @Autowired
  private UserService userService;

  @Autowired
  private TokenProvider tokenProvider;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Operation(summary = "카카오 로그인", description = "카카오 로그인 주소 : kauth.kakao.com/oauth/authorize?client_id=8763097c83420044eeea901b962072ab&redirect_uri=http://localhost:8080/api/oauth/kakao&response_type=code")
  @ResponseBody
  @GetMapping("kakao")
  public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
    try {
      String accessToken = oAuthService.getKakaoAccessToken(code);
      UserDTO userDTO = oAuthService.getKakaoUserInfo(accessToken);

      UserEntity kakaoUser = UserEntity.builder()
          .email(userDTO.getEmail())
          .userName(userDTO.getUserName())
          .password(passwordEncoder.encode(userDTO.getPassword())).build();

      UserEntity registeredUser = userService.create(kakaoUser);

      if (null != registeredUser) {
        final String token = tokenProvider.create(registeredUser);
        final UserDTO responseUserDTO = UserDTO.builder()
            .email(registeredUser.getEmail())
            .id(registeredUser.getId())
            .token(token)
            .build();

        return ResponseEntity.ok().body(responseUserDTO);
      } else {
        ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error("Login failed.").build();
        return ResponseEntity.badRequest().body(responseDTO);
      }
    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
