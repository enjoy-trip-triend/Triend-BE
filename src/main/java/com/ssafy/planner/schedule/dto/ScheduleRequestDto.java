package com.ssafy.planner.schedule.dto;

import java.util.List;

public record ScheduleRequestDto(
    Long plannerId,
    List<ScheduleDto> schedules

) {

}
