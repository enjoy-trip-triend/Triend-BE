package com.ssafy.plan.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plan.dto.PlanCreateRequest;
import com.ssafy.plan.service.PlanService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plans")
public class PlanController {
	
	private final PlanService planService;
	
	@PostMapping
	public ResponseEntity<Long> createPlan(@AuthenticationPrincipal CustomUserDetails loginUser, @RequestBody PlanCreateRequest request) {
 		Long planId = planService.createPlan(request, loginUser);
 		URI location = URI.create("/api/plans/" + planId);
 		System.out.println("플랜 아이디" + planId);
 		return ResponseEntity.created(location).body(planId);
	}
	
    @GetMapping("/images")
    public ResponseEntity<Map<Long, List<String>>> getPresignedImageUrlsByPlanIds(
            @RequestParam List<Long> planIds) {
        return ResponseEntity.ok(planService.getPresignedImageUrlsByPlanIds(planIds));
    }
}
