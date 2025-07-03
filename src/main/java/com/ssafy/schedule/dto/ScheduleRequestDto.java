package com.ssafy.schedule.dto;

import java.util.List;

public record ScheduleRequestDto(
    Long plannerId,
    List<ScheduleDto> schedules

) {

}
