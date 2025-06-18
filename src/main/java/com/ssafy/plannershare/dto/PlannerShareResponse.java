package com.ssafy.plannershare.dto;

import com.ssafy.planner.dto.Planner;

public record PlannerShareResponse(Planner planner, boolean isEditable) {

}
