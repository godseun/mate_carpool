package com.mate.carpool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

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
public class UserTimetableEntity {

  @Id
  @Column(name = "seq")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String seq;

  @Column(name = "user_seq", nullable = false)
  private String userSeq;

  @Column(columnDefinition = "CHAR", length = 1, nullable = false)
  private int dayCode;

}
