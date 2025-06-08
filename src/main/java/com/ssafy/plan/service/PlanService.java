package com.ssafy.plan.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plan.dto.PlanCreateRequest;

public interface PlanService {
	Long createPlan(PlanCreateRequest request, CustomUserDetails loginUser);
	void deletePlan(Long planId, CustomUserDetails loginUser);
	void deletePlansByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay);
	void deletePlansByPlanner(Long plannerId);
	Map<Long, List<String>> getPresignedImageUrlsByPlanIds(List<Long> planIds);
}
