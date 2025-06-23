package com.ssafy.plannershare.dto;

public record PlannerShareStatusResponseDto(
        boolean shared,
        String secretCode
) {
}
