package com.ssafy.schedule.dto;

import java.util.List;

public record ScheduleCreateRequestDto(
    Long plannerId,
    List<ScheduleDto> schedules

) {

}
