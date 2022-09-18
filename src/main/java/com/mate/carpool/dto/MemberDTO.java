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
public class MemberDTO {

  private String token;
  private String email;
  private String memberName;
  private String memberType;
  private String studentNo;
  private String deptNo;
  private String profileImg;

  private List<MemberTimetableDTO> timeTables;

  @JsonIgnore
  private Long memberId;
  @JsonIgnore
  private int isRemoved;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;

  public boolean isMemberTypeValid() {
    if (this.memberType == null || !this.memberType.equals("D") && !this.memberType.equals("P")) {
      return false;
    }
    return true;
  }

}
