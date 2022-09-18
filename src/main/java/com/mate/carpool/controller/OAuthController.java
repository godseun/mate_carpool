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
import com.mate.carpool.dto.MemberDTO;
import com.mate.carpool.model.MemberEntity;
import com.mate.carpool.security.TokenProvider;
import com.mate.carpool.service.OAuthService;
import com.mate.carpool.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "oauth", description = "소셜 로그인 API")
@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

  @Autowired
  private OAuthService oAuthService;

  @Autowired
  private MemberService memberService;

  @Autowired
  private TokenProvider tokenProvider;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Operation(summary = "카카오 로그인", description = "카카오 로그인 주소 : kauth.kakao.com/oauth/authorize?client_id=8763097c83420044eeea901b962072ab&redirect_uri=http://localhost:8080/api/oauth/kakao&response_type=code")
  @ResponseBody
  @GetMapping("kakao")
  public ResponseEntity<?> kakaoCallback(@RequestParam String code) {
    try {
      String accessToken = oAuthService.getKakaoAccessToken(code);
      MemberDTO memberDTO = oAuthService.getKakaoUserInfo(accessToken);

      MemberEntity kakaoUser = MemberEntity.builder()
          .email(memberDTO.getEmail())
          .memberName(memberDTO.getMemberName())
          .password(passwordEncoder.encode(memberDTO.getPassword())).build();

      MemberEntity registeredMember = memberService.create(kakaoUser);

      if (null != registeredMember) {
        final String token = tokenProvider.create(registeredMember);
        final MemberDTO responseMemberDTO = MemberDTO.builder()
            .email(registeredMember.getEmail())
            .memberId(registeredMember.getMemberId())
            .token(token)
            .build();

        return ResponseEntity.ok().body(responseMemberDTO);
      } else {
        ResponseDTO<MemberDTO> responseDTO = ResponseDTO.<MemberDTO>builder().error("Login failed.").build();
        return ResponseEntity.badRequest().body(responseDTO);
      }
    } catch (Exception e) {
      String error = e.getMessage();
      ResponseDTO<MemberDTO> responseDTO = ResponseDTO.<MemberDTO>builder().error(error).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
