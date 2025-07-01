package com.ssafy.planner.service;

import java.util.List;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.dto.ScheduleUpdateRequestDto;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerCreateRequestDto;
import com.ssafy.planner.dto.PlannerUpdateRequesDto;

public interface PlannerService {

  void createPlanner(PlannerCreateRequestDto request, CustomUserDetails loginUser);

  List<Planner> getPlannersByMember(CustomUserDetails loginUser);

  List<Schedule> getSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser);

  List<Schedule> getSchedulesByShared(Long plannerId);

  Planner getPlannerById(Long plannerId, CustomUserDetails loginUser);

  void updatePlanner(Long plannerId, PlannerUpdateRequesDto request, CustomUserDetails loginUser);

  void deletePlanner(Long plannerId, CustomUserDetails loginUser);

  void updateSchedulesForPlanner(Long plannerId, List<ScheduleUpdateRequestDto> requests,
      CustomUserDetails loginUser);

  void deleteSchedulesForPlanner(Long planId, List<Long> planIdList, CustomUserDetails loginUser);
}
