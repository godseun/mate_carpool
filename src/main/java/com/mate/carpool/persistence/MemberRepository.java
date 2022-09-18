package com.mate.carpool.persistence;

import com.mate.carpool.model.MemberEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

  // Optional<MemberEntity> findByMemberId(Long memberId);

  MemberEntity findByEmail(String email);

  Boolean existsByEmail(String email);

  MemberEntity findByEmailAndPassword(String email, String password);
}
