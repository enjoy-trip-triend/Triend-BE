package com.ssafy.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleDto(

    LocalDate date,
    LocalTime startTime,
    String content,
    String placeUrl,
    Integer idx,
    Long placeId
) {

}
