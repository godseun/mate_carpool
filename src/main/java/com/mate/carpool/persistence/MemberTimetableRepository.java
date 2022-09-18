package com.mate.carpool.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mate.carpool.model.MemberTimetableEntity;

public interface MemberTimetableRepository extends JpaRepository<MemberTimetableEntity, String> {

  List<MemberTimetableEntity> findByMemberId(Long memberId);

  @Transactional
  void deleteAllByMemberId(Long memberId);

  // Boolean existsByDayCode(String memberId, int dayCode);
}
