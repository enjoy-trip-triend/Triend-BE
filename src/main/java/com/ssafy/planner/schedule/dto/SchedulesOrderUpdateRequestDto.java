package com.ssafy.schedule.dto;

import java.time.LocalDate;
import java.util.List;

public record SchedulesOrderUpdateRequestDto(
        Long plannerId,
        LocalDate date,
        List<ScheduleOrderDto> recommendSchedules
) {

}
