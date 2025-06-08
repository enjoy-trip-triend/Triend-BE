package com.ssafy.planner.service;

import java.util.List;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plan.dto.Plan;
import com.ssafy.plan.dto.PlanUpdateRequest;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerCreateRequest;
import com.ssafy.planner.dto.PlannerUpdateRequest;

public interface PlannerService {
	
	void createPlanner(PlannerCreateRequest request, CustomUserDetails loginUser);
	List<Planner> getPlannersByMember(CustomUserDetails loginUser);
	List<Plan> getPlansByPlanner(Long plannerId, CustomUserDetails loginUser);
	List<Plan> getPlansByShared(Long plannerId);
	Planner getPlannerById(Long plannerId, CustomUserDetails loginUser);
	void updatePlanner(Long plannerId, PlannerUpdateRequest request, CustomUserDetails loginUser);
	void deletePlanner(Long plannerId, CustomUserDetails loginUser);
	void updatePlansForPlanner(Long plannerId, List<PlanUpdateRequest> requests, CustomUserDetails loginUser);
	void deletePlansForPlanner(Long planId, List<Long> planIdList, CustomUserDetails loginUser);
}
