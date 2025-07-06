package com.ssafy.planner.plannershare.service;


import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.plannershare.dto.PlannerShareCreateResponseDto;
import com.ssafy.planner.plannershare.dto.PlannerShareResponseDto;
import com.ssafy.planner.plannershare.dto.PlannerShareStatusResponseDto;

public interface PlannerShareService {
    PlannerShareStatusResponseDto getPlannerShareStatus(Long plannerId);

    PlannerShareCreateResponseDto createSecreteCode(Long plannerId, CustomUserDetails loginUser, String password);

    void verifyPassword(String secretCode, String password);

    PlannerShareResponseDto getSharedPlanner(String secretCode, CustomUserDetails loginUser);

    void addMemberToPlannerMember(String secretCode, CustomUserDetails loginUser);
}
