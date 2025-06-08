package com.ssafy.plan.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PlanCreateRequest(
	    Long plannerId,
	    LocalDate date,
	    LocalTime startTime,
	    LocalTime endTime,
	    String placeName,
	    String address,
	    String content,
	    double lat,
	    double lon,
	    String placeUrl
	) {}
