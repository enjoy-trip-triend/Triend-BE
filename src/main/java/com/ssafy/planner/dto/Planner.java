package com.ssafy.planner.dto;

import java.time.LocalDate;

import com.ssafy.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planner {
	private Long id;
	private LocalDate startDay;
	private LocalDate endDay;
	private String name;
	private String location;
	private Member member;
}
