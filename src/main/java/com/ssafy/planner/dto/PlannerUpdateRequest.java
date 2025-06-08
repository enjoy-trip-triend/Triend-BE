package com.ssafy.planner.dto;

import java.time.LocalDate;

import groovy.transform.ToString;

@ToString
public record PlannerUpdateRequest(
		Long id,
		LocalDate startDay,
		LocalDate endDay,
		String name,
		String location
		) {

}
