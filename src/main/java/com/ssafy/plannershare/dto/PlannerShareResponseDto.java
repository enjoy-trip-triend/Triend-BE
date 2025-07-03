package com.ssafy.plannershare.dto;

import com.ssafy.planner.dto.Planner;
import com.ssafy.schedule.dto.ScheduleResponseDto;
import java.util.List;

public record PlannerShareResponseDto(
        Planner planner,
        List<ScheduleResponseDto> schedules,
        boolean isEditable
) {

}
