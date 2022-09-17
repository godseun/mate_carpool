package com.mate.carpool.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

  private String token;
  private String email;
  private String userName;
  private String userType;
  private String studentNo;
  private String deptNo;
  private String profileImg;

  @JsonIgnore
  private String id;
  @JsonIgnore
  private int isRemoved;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;

}
