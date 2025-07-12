package com.ssafy.planner.schedule.service;

import com.ssafy.planner.dto.PlannerUpdateRequestDto;
import com.ssafy.planner.schedule.dto.ScheduleResponseDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.schedule.dto.ScheduleRequestDto;

public interface ScheduleService {
	void createSchedules(Long plannerId, ScheduleRequestDto request, CustomUserDetails loginUser);
	List<ScheduleResponseDto> getSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser);
	void deleteSchedule(Long planId, CustomUserDetails loginUser);
	void deleteSchedulesByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay);
	void deleteSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser);
	Map<Long, List<String>> getPresignedImageUrlsByScheduleIds(List<Long> planIds);
	void updateSchedules(Long plannerId, ScheduleRequestDto request, CustomUserDetails loginUser);
	void updateSchedulesDate(Long plannerId, PlannerUpdateRequestDto oldPlanner, CustomUserDetails loginUser);
}
