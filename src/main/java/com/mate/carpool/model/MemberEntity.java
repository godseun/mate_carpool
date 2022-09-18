package com.mate.carpool.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class MemberEntity {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String memberId;

  @Column
  private String memberName;

  @Column
  private String email;

  @Column
  private String password;

  @Column
  private String memberType;

  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT", length = 1)
  private int isRemoved;

  @Column
  private String studentNo;

  @Column
  private String deptNo;

  @Column
  private String profileImg;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id")
  private List<MemberTimetableEntity> timeTables;

  @Generated(GenerationTime.INSERT)
  @Column
  private LocalDateTime createdAt;

  @Generated(GenerationTime.ALWAYS)
  @Column
  private LocalDateTime updatedAt;

}
