package com.mate.carpool.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "match", description = "카풀 API")
@RestController
@RequestMapping("api/match")
public class PoolMatchController {
  @Operation(summary = "find all match", description = "모든 카풀 데이터 가져오기")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })
  @GetMapping
  public String findItemAll() {
    return "카풀 서비스 풀서치";
  }

  @Operation(summary = "find id match", description = "카풀 데이터 가져오기")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })
  @Parameters({
      @Parameter(name = "pId", description = "카풀 index", example = "7"),
  })
  @GetMapping("{pId}")
  public String findItem(@RequestParam String pId) {
    return "카풀 서비스 서치 : " + pId;
  }

}
