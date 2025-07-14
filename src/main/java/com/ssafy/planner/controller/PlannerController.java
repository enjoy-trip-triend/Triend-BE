package com.ssafy.planner.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerCreateRequestDto;
import com.ssafy.planner.dto.PlannerUpdateRequestDto;
import com.ssafy.planner.service.PlannerService;
import com.ssafy.planner.schedule.dto.ScheduleResponseDto;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planners")
public class PlannerController {

    private final PlannerService plannerService;

    @PostMapping
    public ResponseEntity<Void> createPlanner(@AuthenticationPrincipal CustomUserDetails loginUser,
            @RequestBody PlannerCreateRequestDto request) {
        plannerService.createPlanner(request, loginUser);
        return ResponseEntity.created(null)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<Planner>> getPlanners(
            @AuthenticationPrincipal CustomUserDetails loginUser) {
        return ResponseEntity.ok(plannerService.getPlannersByMember(loginUser));
    }

    @GetMapping("/{planner-id}")
    public ResponseEntity<Planner> getPlannerById(
            @AuthenticationPrincipal CustomUserDetails loginUser,
            @PathVariable("planner-id") Long plannerId) {
        return ResponseEntity.ok(plannerService.getPlannerById(plannerId, loginUser));
    }

    @PutMapping("/{planner-id}")
    public ResponseEntity<Void> updatePlanner(@AuthenticationPrincipal CustomUserDetails loginUser,
            @PathVariable("planner-id") Long plannerId,
            @RequestBody PlannerUpdateRequestDto request) {
        plannerService.updatePlanner(plannerId, request, loginUser);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{planner-id}")
    public ResponseEntity<Planner> deletePlanner(
            @AuthenticationPrincipal CustomUserDetails loginUser,
            @PathVariable("planner-id") Long plannerId) {
        plannerService.deletePlanner(plannerId, loginUser);
        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping("/{planner-id}/shared")
    public ResponseEntity<List<ScheduleResponseDto>> getPlansByShared(
            @PathVariable("planner-id") Long plannerId) {
        return ResponseEntity.ok(plannerService.getSchedulesByShared(plannerId));
    }
}