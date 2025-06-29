package com.ssafy.plannershare.dto;

import com.ssafy.planner.dto.Planner;
import com.ssafy.schedule.dto.Schedule;
import java.util.List;

public record PlannerShareResponseDto(Planner planner, List<Schedule> plans, boolean isEditable) {

}
