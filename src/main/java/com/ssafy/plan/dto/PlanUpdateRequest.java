package com.ssafy.plan.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record PlanUpdateRequest(
		Long id,
		LocalDate date,
		LocalTime startTime,
		LocalTime endTime,
		String placeName,
		String address,
		String content,
		double lat,
		double lon,
		String placeUrl
) {
}
