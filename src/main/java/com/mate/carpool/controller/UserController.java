package com.mate.carpool.controller;

import com.mate.carpool.dto.ResponseDTO;
import com.mate.carpool.dto.UserDTO;
import com.mate.carpool.model.UserEntity;
import com.mate.carpool.security.TokenProvider;
import com.mate.carpool.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private TokenProvider tokenProvider;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Operation(summary = "signup", description = "회원가입")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
      @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
  })
  @Parameters({
      @Parameter(name = "email", description = "자신의 email", example = "passinger@mate.com"),
      @Parameter(name = "userName", description = "자신의 닉네임", example = "메이트"),
  })
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
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
      ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
    UserEntity user = userService.getByCredentials(
        userDTO.getEmail(),
        userDTO.getPassword(),
        passwordEncoder);

    if (null != user) {
      final String token = tokenProvider.create(user);
      final UserDTO responseUserDTO = UserDTO.builder()
          .email(user.getEmail())
          .id(user.getId())
          .token(token).build();

      return ResponseEntity.ok().body(responseUserDTO);
    } else {
      ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error("Login failed.").build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
