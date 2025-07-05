package com.ssafy.schedule.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.schedule.dto.ScheduleRequestDto;

public interface ScheduleService {
	void createSchedules(ScheduleRequestDto request, CustomUserDetails loginUser);
	void deleteSchedule(Long planId, CustomUserDetails loginUser);
	void deleteSchedulesByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay);
	void deleteSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser);
	Map<Long, List<String>> getPresignedImageUrlsByScheduleIds(List<Long> planIds);
}
