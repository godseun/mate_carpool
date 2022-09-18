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
public class PassengerDTO {

  private String ticketId;
  private String passengerId;

  private LocalDateTime createdAt;
  @JsonIgnore
  private LocalDateTime updatedAt;
}
