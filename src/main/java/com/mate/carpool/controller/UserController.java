package com.mate.carpool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mate.carpool.dto.ResponseDTO;
import com.mate.carpool.dto.UserDTO;
import com.mate.carpool.dto.UserTimetableDTO;
import com.mate.carpool.model.UserEntity;
import com.mate.carpool.model.UserTimetableEntity;
import com.mate.carpool.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "account", description = "계정 관리 API")
@RestController
@RequestMapping("/api/account")
public class UserController {

  @Autowired
  private UserService userService;

  @Operation(summary = "자신의 정보 가져오기")
  @ResponseBody
  @GetMapping
  public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal String userSeq) {
    // TODO: 사용자 정보가져오기 / 에러핸들링 필요
    try {
      final Optional<UserEntity> userEntity = userService.getMyInfo(userSeq);
      final List<UserTimetableDTO> resUserTimetables = new ArrayList<>();
      userEntity.get().getTimeTables().forEach(timeTable -> {
        UserTimetableDTO userTimetableDTO = UserTimetableDTO.builder().dayCode(timeTable.getDayCode()).build();
        resUserTimetables.add(userTimetableDTO);
      });
      final UserDTO responseUserDTO = UserDTO.builder()
          .email(userEntity.get().getEmail())
          .userName(userEntity.get().getUserName())
          .userType(userEntity.get().getUserType())
          .studentNo(userEntity.get().getStudentNo())
          .deptNo(userEntity.get().getDeptNo())
          .profileImg(userEntity.get().getProfileImg())
          .timeTables(resUserTimetables)
          .build();
      return ResponseEntity.ok().body(responseUserDTO);
    } catch (Exception e) {
      ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @Operation(summary = "드라이버 or 패신저 최초 등록", description = "")
  @ResponseBody
  @PatchMapping
  public ResponseEntity<?> userTypeCreate(@AuthenticationPrincipal String userSeq, @RequestBody UserDTO userDTO) {
    // TODO: 사용자 정보가져오기 / 에러핸들링 필요
    try {

      if (!userDTO.isUserTypeValid()) {
        ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error("유저 타입이 맞지않습니다.").build();
        return ResponseEntity.badRequest().body(responseDTO);
      }

      List<UserTimetableDTO> timeTables = userDTO.getTimeTables();
      List<UserTimetableEntity> timetableEntities = new ArrayList<>();
      timeTables.forEach(timeTable -> {
        UserTimetableEntity userTimetableEntity = UserTimetableEntity.builder()
            .userSeq(userSeq)
            .dayCode(timeTable.getDayCode())
            .build();
        timetableEntities.add(userTimetableEntity);
      });

      UserEntity userEntity = UserEntity.builder()
          .userSeq(userSeq)
          .studentNo(userDTO.getStudentNo())
          .deptNo(userDTO.getDeptNo())
          .userType(userDTO.getUserType())
          .timeTables(timetableEntities)
          .build();

      final Optional<UserEntity> responseEntity = userService.update(userSeq, userEntity);
      final List<UserTimetableDTO> resUserTimetables = new ArrayList<>();
      responseEntity.get().getTimeTables().forEach(timeTable -> {
        UserTimetableDTO userTimetableDTO = UserTimetableDTO.builder().dayCode(timeTable.getDayCode()).build();
        resUserTimetables.add(userTimetableDTO);
      });
      final UserDTO responseUserDTO = UserDTO.builder()
          .email(responseEntity.get().getEmail())
          .userName(responseEntity.get().getUserName())
          .userType(responseEntity.get().getUserType())
          .studentNo(responseEntity.get().getStudentNo())
          .deptNo(responseEntity.get().getDeptNo())
          .profileImg(responseEntity.get().getProfileImg())
          .timeTables(resUserTimetables)
          .build();
      return ResponseEntity.ok().body(responseUserDTO);
    } catch (Exception e) {
      ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }
}
