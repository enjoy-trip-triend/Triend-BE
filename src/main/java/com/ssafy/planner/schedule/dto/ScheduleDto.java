package com.ssafy.planner.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDto(
    Long id,
    LocalDate date,
    LocalTime startTime,
    String content,
    String placeUrl,
    Integer idx,
    Long placeId
) {

}
