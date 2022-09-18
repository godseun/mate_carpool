package com.mate.carpool.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class TicketEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long ticketId;

  @Column
  private String driverId;

  @Column
  private String startPoint;
  @Column
  private String endPoint;
  @Column
  private LocalDateTime startTime;
  @Column
  private String kakaoOpenChatUrl;
  @Column
  private String kakaoOpenChatTitle;
  @Column
  private int recruitPerson;

  @Generated(GenerationTime.INSERT)
  @Column
  private LocalDateTime createdAt;
  @Generated(GenerationTime.ALWAYS)
  @Column
  private LocalDateTime updatedAt;

}
