package com.mate.carpool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mate.carpool.dto.ResponseDTO;
import com.mate.carpool.dto.MemberDTO;
import com.mate.carpool.dto.MemberTimetableDTO;
import com.mate.carpool.model.MemberEntity;
import com.mate.carpool.model.MemberTimetableEntity;
import com.mate.carpool.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "account", description = "계정 관리 API")
@RestController
@RequestMapping("/api/account")
public class MemberController {

  @Autowired
  private MemberService memberService;

  @Operation(summary = "자신의 정보 가져오기")
  @ResponseBody
  @GetMapping
  public ResponseEntity<?> getMemberInfo(@AuthenticationPrincipal Long memberId) {
    // TODO: 사용자 정보가져오기 / 에러핸들링 필요
    try {
      final Optional<MemberEntity> memberEntity = memberService.getMyInfo(memberId);
      final List<MemberTimetableDTO> resMemberTimetables = new ArrayList<>();
      memberEntity.get().getTimeTables().forEach(timeTable -> {
        MemberTimetableDTO memberTimetableDTO = MemberTimetableDTO.builder().dayCode(timeTable.getDayCode()).build();
        resMemberTimetables.add(memberTimetableDTO);
      });
      final MemberDTO responseMemberDTO = MemberDTO.builder()
          .email(memberEntity.get().getEmail())
          .memberName(memberEntity.get().getMemberName())
          .memberType(memberEntity.get().getMemberType())
          .studentNo(memberEntity.get().getStudentNo())
          .deptNo(memberEntity.get().getDeptNo())
          .profileImg(memberEntity.get().getProfileImg())
          .timeTables(resMemberTimetables)
          .build();
      return ResponseEntity.ok().body(responseMemberDTO);
    } catch (Exception e) {
      ResponseDTO<MemberDTO> responseDTO = ResponseDTO.<MemberDTO>builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @Operation(summary = "자신의 정보 업데이트", description = "")
  @ResponseBody
  @PatchMapping
  public ResponseEntity<?> memberTypeUpdate(@AuthenticationPrincipal Long memberId,
      @RequestBody MemberDTO memberDTO) {
    // TODO: 사용자 정보가져오기 / 에러핸들링 필요
    try {

      if (!memberDTO.isMemberTypeValid()) {
        ResponseDTO<MemberDTO> responseDTO = ResponseDTO.<MemberDTO>builder().error("유저 타입이 맞지않습니다.").build();
        return ResponseEntity.badRequest().body(responseDTO);
      }

      List<MemberTimetableDTO> timeTables = memberDTO.getTimeTables();
      List<MemberTimetableEntity> timetableEntities = new ArrayList<>();
      timeTables.forEach(timeTable -> {
        MemberTimetableEntity memberTimetableEntity = MemberTimetableEntity.builder()
            .memberId(memberId)
            .dayCode(timeTable.getDayCode())
            .build();
        timetableEntities.add(memberTimetableEntity);
      });

      MemberEntity memberEntity = MemberEntity.builder()
          .memberId(memberId)
          .studentNo(memberDTO.getStudentNo())
          .deptNo(memberDTO.getDeptNo())
          .memberType(memberDTO.getMemberType())
          .timeTables(timetableEntities)
          .build();

      final Optional<MemberEntity> responseEntity = memberService.update(memberId, memberEntity);
      final List<MemberTimetableDTO> resMemberTimetables = new ArrayList<>();
      responseEntity.get().getTimeTables().forEach(timeTable -> {
        MemberTimetableDTO memberTimetableDTO = MemberTimetableDTO.builder().dayCode(timeTable.getDayCode()).build();
        resMemberTimetables.add(memberTimetableDTO);
      });
      final MemberDTO responseMemberDTO = MemberDTO.builder()
          .email(responseEntity.get().getEmail())
          .memberName(responseEntity.get().getMemberName())
          .memberType(responseEntity.get().getMemberType())
          .studentNo(responseEntity.get().getStudentNo())
          .deptNo(responseEntity.get().getDeptNo())
          .profileImg(responseEntity.get().getProfileImg())
          .timeTables(resMemberTimetables)
          .build();
      return ResponseEntity.ok().body(responseMemberDTO);
    } catch (Exception e) {
      ResponseDTO<MemberDTO> responseDTO = ResponseDTO.<MemberDTO>builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
