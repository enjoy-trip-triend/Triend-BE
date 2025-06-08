package com.ssafy.planner.dto;

import java.time.LocalDate;

public record PlannerCreateRequest (
		LocalDate startDay,
		LocalDate endDay,
		String name,
		String location
		) {

}
