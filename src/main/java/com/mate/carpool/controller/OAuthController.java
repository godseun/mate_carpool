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

  @ResponseBody
  @GetMapping("kakao")
  public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
    try {
      String accessToken = oAuthService.getKakaoAccessToken(code);
      UserDTO userDTO = oAuthService.getKakaoUserInfo(accessToken);

      UserEntity user = UserEntity.builder()
          .email(userDTO.getEmail())
          .userName(userDTO.getUserName())
          .password(passwordEncoder.encode(userDTO.getPassword())).build();

      UserEntity registeredUser = userService.create(user);

      UserDTO responseUserDTO = UserDTO.builder()
          .email(registeredUser.getEmail())
          .id(registeredUser.getId())
          .userName(registeredUser.getUserName()).build();

      return ResponseEntity.ok().body(responseUserDTO);
    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<UserDTO> response = ResponseDTO.<UserDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(response);
    }

  }

}
