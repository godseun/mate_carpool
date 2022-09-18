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
public class MemberTimetableDTO {

  @JsonIgnore
  private String memberId;

  private int dayCode;

  @JsonIgnore
  private String memberTimetableId;
}
