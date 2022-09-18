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
public class TicketDTO {

  private Long ticketId;
  private Long driverId;
  private String startPoint;
  private String endPoint;
  private LocalDateTime startTime;
  private String kakaoOpenChatUrl;
  private String kakaoOpenChatTitle;
  private int recruitPerson;

  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;
}
