package com.ssafy.plannershare.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plannershare.dto.PlannerShareCreateRequest;
import com.ssafy.plannershare.dto.PlannerShareResponse;
import com.ssafy.plannershare.dto.PlannerShareVerifyRequest;
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
@RequestMapping("/api/planners-share")
public class PlannerShareController {
    private final PlannerShareService plannerShareService;

    // 공유 링크 생성 (비밀번호까지 설정)
    @PostMapping("/{planner-id}")
    public ResponseEntity<Map<String, String>> createShare(
            @AuthenticationPrincipal CustomUserDetails loginUser,
            @PathVariable("planner-id") Long plannerId,
            @RequestBody PlannerShareCreateRequest request) {
        String secretCode = plannerShareService.createSecreteCode(plannerId, loginUser, request.password());
        return ResponseEntity.ok(Map.of("secretCode", secretCode));
    }

    // 공유 플래너 비밀번호 검증
    @PostMapping("/{secretCode}/verify")
    public ResponseEntity<Void> verifyPassword(
            @PathVariable String secretCode,
            @RequestBody PlannerShareVerifyRequest request
    ) {
        plannerShareService.verifyPassword(secretCode, request.password());
        return ResponseEntity.noContent().build();
    }

    // 공유 링크 조회
    @GetMapping("/{secretCode}")
    public ResponseEntity<PlannerShareResponse> getSharedPlanner(
            @PathVariable String secretCode,
            @AuthenticationPrincipal CustomUserDetails loginUser
    ) {
        return ResponseEntity.ok(plannerShareService.getSharedPlanner(secretCode, loginUser));
    }

}
