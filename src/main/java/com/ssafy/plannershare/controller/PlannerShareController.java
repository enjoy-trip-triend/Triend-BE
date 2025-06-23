package com.ssafy.plannershare.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plannershare.dto.PlannerShareCreateRequestDto;
import com.ssafy.plannershare.dto.PlannerShareCreateResponseDto;
import com.ssafy.plannershare.dto.PlannerShareResponseDto;
import com.ssafy.plannershare.dto.PlannerShareStatusResponseDto;
import com.ssafy.plannershare.dto.PlannerShareVerifyRequestDto;
import com.ssafy.plannershare.service.PlannerShareService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planners/{planner-id}/share")
public class PlannerShareController {
    private final PlannerShareService plannerShareService;

    // 공유 링크 조회
    @GetMapping
    public ResponseEntity<PlannerShareStatusResponseDto> getShareStatus(
            @PathVariable("planner-id") Long plannerId) {
        return ResponseEntity.ok(plannerShareService.getPlannerShareStatus(plannerId));
    }

    // 공유 링크 생성 (비밀번호까지 설정)
    @PostMapping
    public ResponseEntity<PlannerShareCreateResponseDto> createShare(
            @AuthenticationPrincipal CustomUserDetails loginUser,
            @PathVariable("planner-id") Long plannerId,
            @RequestBody PlannerShareCreateRequestDto request) {
        return ResponseEntity.ok(plannerShareService.createSecreteCode(plannerId, loginUser, request.password()));
    }

    // 공유 플래너 비밀번호 검증
    @PostMapping("/{secretCode}/verify")
    public ResponseEntity<Void> verifyPassword(
            @PathVariable("planner-id") Long plannerId,
            @PathVariable String secretCode,
            @RequestBody PlannerShareVerifyRequestDto request
    ) {
        plannerShareService.verifyPassword(secretCode, request.password());
        return ResponseEntity.noContent().build();
    }

    // 공유 링크 조회
    @GetMapping("/{secretCode}")
    public ResponseEntity<PlannerShareResponseDto> getSharedPlanner(
            @PathVariable("planner-id") Long plannerId,
            @PathVariable String secretCode,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        return ResponseEntity.ok(plannerShareService.getSharedPlanner(secretCode, loginUser));
    }

    // 참여자 등록
    @PostMapping("/{secretCode}/join")
    public ResponseEntity<Void> joinPlannerShare(
            @PathVariable("planner-id") Long plannerId,
            @PathVariable String secretCode,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        plannerShareService.addMemberToPlannerMember(secretCode, loginUser);
        return ResponseEntity.noContent().build();
    }
}
