package com.mate.carpool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mate.carpool.service.OAuthService;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

  @Autowired
  private OAuthService oAuthService;

  @ResponseBody
  @GetMapping("kakao")
  public void kakaoCallback(@RequestParam String code) {
    System.out.println(code);
    oAuthService.getKakaoAccessToken(code);
  }

  @ResponseBody
  @GetMapping("kakao_login")
  public void kakaoLogin() {
    System.out.println("kakao_login success ");
  }

}
