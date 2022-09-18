package com.mate.carpool.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTimetableDTO {

  @JsonIgnore
  private String userSeq;

  private int dayCode;

  @JsonIgnore
  private String seq;
}
