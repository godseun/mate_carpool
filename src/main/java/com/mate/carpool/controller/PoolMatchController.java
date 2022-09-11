package com.mate.carpool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/match")
public class PoolMatchController {
  @GetMapping
  public String findItemAll() {
    return "카풀 서비스 풀서치";
  }

  @GetMapping("{pId}")
  public String findItem(@RequestParam String pId) {
    return "카풀 서비스 서치 : " + pId;
  }

}
