package com.mate.carpool.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mate.carpool.dto.ResponseDTO;
import com.mate.carpool.dto.TicketDTO;
import com.mate.carpool.service.TicketService;

import antlr.collections.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "match", description = "카풀 API")
@RestController
@RequestMapping("api/match")
public class PoolMatchController {

  @Autowired
  private TicketService ticketService;

  @Operation(summary = "find all match", description = "모든 카풀 데이터 가져오기")
  @GetMapping
  public ResponseEntity<?> carPoolSelectAll() {

    try {
      // final Optional<UserEntity> userEntity = userService.getMyInfo();
      // final List<PassengerDTO> resUserTimetables = new ArrayList<>();
      // userEntity.get().getTimeTables().forEach(timeTable -> {
      // UserTimetableDTO userTimetableDTO =
      // UserTimetableDTO.builder().dayCode(timeTable.getDayCode()).build();
      // resUserTimetables.add(userTimetableDTO);
      // });
      // final TicketDTO responseUserDTO = TicketDTO.builder()
      // .email(userEntity.get().getEmail())
      // .userName(userEntity.get().getUserName())
      // .userType(userEntity.get().getUserType())
      // .studentNo(userEntity.get().getStudentNo())
      // .deptNo(userEntity.get().getDeptNo())
      // .profileImg(userEntity.get().getProfileImg())
      // .timeTables(resUserTimetables)
      // .build();
      return ResponseEntity.ok().body("");
    } catch (Exception e) {
      ResponseDTO<TicketDTO> responseDTO = ResponseDTO.<TicketDTO>builder().error(e.getMessage()).build();
      return ResponseEntity.badRequest().body(responseDTO);
    }
  }

  @Operation(summary = "find id match", description = "카풀 데이터 가져오기")
  @GetMapping("{pId}")
  public String carPoolSelect(@RequestParam String pId) {
    return "카풀 서비스 서치 : " + pId;
  }

}
