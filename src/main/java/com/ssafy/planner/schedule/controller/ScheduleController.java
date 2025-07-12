package com.ssafy.planner.schedule.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.PlannerUpdateRequestDto;
import com.ssafy.planner.schedule.dto.ScheduleRequestDto;
import com.ssafy.planner.schedule.dto.ScheduleResponseDto;
import com.ssafy.planner.schedule.service.ScheduleService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planners/{planner-id}/schedules")
public class ScheduleController {

  private final ScheduleService scheduleService;

  @PostMapping
  public ResponseEntity<Long> createSchedules(@AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable("planner-id") Long plannerId, @RequestBody ScheduleRequestDto request) {
    scheduleService.createSchedules(plannerId, request, loginUser);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()   // /api/schedules
        .build()
        .toUri();

    return ResponseEntity.created(location)
        .build();
  }

  @GetMapping
  public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByPlanner(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable("planner-id") Long plannerId) {
    return ResponseEntity.ok(scheduleService.getSchedulesByPlanner(plannerId, loginUser));
  }

  @PutMapping
  public ResponseEntity<Void> updateSchedules(@AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable("planner-id") Long plannerId, @RequestBody ScheduleRequestDto request) {
    scheduleService.updateSchedules(plannerId, request, loginUser);
    return ResponseEntity.noContent()
        .build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteSchedules(@AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable("planner-id") Long plannerId) {
    scheduleService.deleteSchedulesByPlanner(plannerId, loginUser);
    return ResponseEntity.noContent()
        .build();
  }

  @PutMapping("/date")
  public ResponseEntity<Void> updateSchedulesDate(
      @AuthenticationPrincipal CustomUserDetails loginUser,
      @PathVariable("planner-id") Long plannerId, @RequestBody PlannerUpdateRequestDto oldPlanner) {
    scheduleService.updateSchedulesDate(plannerId, oldPlanner, loginUser);
    return ResponseEntity.noContent()
        .build();
  }

  @GetMapping("/images")
  public ResponseEntity<Map<Long, List<String>>> getPresignedImageUrlsByScheduleIds(
      @RequestParam List<Long> scheduleIds) {
    return ResponseEntity.ok(scheduleService.getPresignedImageUrlsByScheduleIds(scheduleIds));
  }
}
