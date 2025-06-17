package com.ssafy.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleCreateRequest(
        Long plannerId,
        LocalDate date,
        LocalTime startTime,
        Long placeId,
        String content,
        String placeUrl,
        Integer idx
) {
    
}
