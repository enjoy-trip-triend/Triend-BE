package com.ssafy.schedule.dto;

import java.util.List;

public record ScheduleUpdateRequestDto(
    Long id,
    List<ScheduleDto> schedules
) {

}
