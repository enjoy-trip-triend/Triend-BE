package com.ssafy.plannershare.service;


import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plannershare.dto.PlannerShareResponse;

public interface PlannerShareService {

    String createSecreteCode(Long plannerId, CustomUserDetails loginUser, String password);

    void verifyPassword(String secretCode, String password);

    PlannerShareResponse getSharedPlanner(String secretCode, CustomUserDetails loginUser);
}
