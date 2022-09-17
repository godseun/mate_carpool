package com.mate.carpool.dto;

import java.time.LocalDateTime;
import java.util.List;

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

  private List<UserTimetableDTO> timeTables;

  @JsonIgnore
  private String userSeq;
  @JsonIgnore
  private int isRemoved;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;

}
