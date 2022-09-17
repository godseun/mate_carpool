package com.mate.carpool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mate.carpool.dto.TestDTO;

@RestController
@RequestMapping("test")
public class TestController {

  @GetMapping
  public String findAll() {
    return "Hello Mate Team";
  }

  @PostMapping
  public String jsonTest(@RequestBody TestDTO testDTO) {
    return testDTO.toString();
  }
}
