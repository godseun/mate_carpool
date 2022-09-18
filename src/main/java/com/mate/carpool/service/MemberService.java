package com.mate.carpool.service;

import com.mate.carpool.model.MemberEntity;
import com.mate.carpool.persistence.MemberRepository;
import com.mate.carpool.persistence.MemberTimetableRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberTimetableRepository memberTimetableRepository;

  public MemberEntity create(final MemberEntity memberEntity) {
    if (null == memberEntity || null == memberEntity.getEmail()) {
      throw new RuntimeException("Invalid arguments");
    }
    final String email = memberEntity.getEmail();
    if (memberRepository.existsByEmail(email)) {
      log.debug("MemberService.create Email already exists {}", email);
      return memberRepository.findByEmail(email);
    }
    return memberRepository.save(memberEntity);
  }

  public Optional<MemberEntity> getMyInfo(final Long memberId) {
    return memberRepository.findByMemberId(memberId);
  }

  public Optional<MemberEntity> update(final Long memberId, final MemberEntity memberEntity) {
    // TODO : 에러핸들링필요

    memberTimetableRepository.deleteAllByMemberId(memberId);

    memberTimetableRepository.saveAll(memberEntity.getTimeTables());

    Optional<MemberEntity> original = memberRepository.findByMemberId(memberId);

    original.get().setStudentNo(memberEntity.getStudentNo());
    original.get().setDeptNo(memberEntity.getDeptNo());
    original.get().setMemberType(memberEntity.getMemberType());

    memberRepository.save(original.get());

    return getMyInfo(memberId);
  }
}
