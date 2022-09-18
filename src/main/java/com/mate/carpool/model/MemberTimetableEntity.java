package com.mate.carpool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

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
public class MemberTimetableEntity {

  @Id
  @Column(name = "member_timetable_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long memberTimetableId;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @Column(columnDefinition = "CHAR", length = 1, nullable = false)
  private int dayCode;

}
