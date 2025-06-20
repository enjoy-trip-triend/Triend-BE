package com.ssafy.plannershare.service;


import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plannershare.dto.PlannerShareResponseDto;

public interface PlannerShareService {

    String createSecreteCode(Long plannerId, CustomUserDetails loginUser, String password);

    void verifyPassword(String secretCode, String password);

    PlannerShareResponseDto getSharedPlanner(String secretCode, CustomUserDetails loginUser);

    void addMemberToPlannerMember(String secretCode, CustomUserDetails loginUser);
}
