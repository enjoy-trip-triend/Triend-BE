package com.ssafy.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleUpdateRequest(
		Long id,
		LocalDate date,
		LocalTime startTime,
		LocalTime endTime,
		Long placeId,
		String content,
		String placeUrl
) {
}
