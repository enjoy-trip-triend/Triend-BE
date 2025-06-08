package com.ssafy.plan.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ssafy.planner.dto.Planner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {
	private Long id;
	private Planner planner;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String placeName;
	private String address;
	private String content;
	private double lat;
	private double lon;
	private String placeUrl;
}
