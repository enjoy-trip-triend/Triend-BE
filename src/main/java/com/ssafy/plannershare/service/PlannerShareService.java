package com.ssafy.plannershare.service;


import com.ssafy.common.security.dto.CustomUserDetails;

public interface PlannerShareService {

    String createSecreteCode(Long plannerId, CustomUserDetails loginUser, String password);

    void verifyPassword(String secretCode, String password);
}
