package com.mate.carpool.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mate.carpool.model.UserTimetableEntity;

public interface UserTimetableRepository extends JpaRepository<UserTimetableEntity, String> {

  List<UserTimetableEntity> findByUserSeq(String userSeq);

  @Transactional
  void deleteAllByUserSeq(String userSeq);

  // Boolean existsByDayCode(String userSeq, int dayCode);
}
