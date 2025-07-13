package com.ssafy.schedule.dto;

import java.time.LocalDate;
import java.util.List;

public record ScheduleOrderUpdateRequestDto(
        LocalDate date,
        List<ScheduleOrderDto> schedules
) {

}
