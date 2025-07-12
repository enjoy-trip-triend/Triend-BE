package com.ssafy.planner.plannershare.dto;

public record PlannerShareStatusResponseDto(
        boolean shared,
        String secretCode
) {
}
