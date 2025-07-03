package com.ssafy.planner.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerRequestDto;
import com.ssafy.schedule.dto.ScheduleRequestDto;
import com.ssafy.schedule.dto.ScheduleResponseDto;
import java.util.List;

public interface PlannerService {

  void createPlanner(PlannerRequestDto request, CustomUserDetails loginUser);

  List<Planner> getPlannersByMember(CustomUserDetails loginUser);

  List<ScheduleResponseDto> getSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser);

  List<ScheduleResponseDto> getSchedulesByShared(Long plannerId);

  Planner getPlannerById(Long plannerId, CustomUserDetails loginUser);

  void updatePlanner(Long plannerId, PlannerRequestDto request, CustomUserDetails loginUser);

  void deletePlanner(Long plannerId, CustomUserDetails loginUser);

  void updateSchedulesForPlanner(Long plannerId, List<ScheduleRequestDto> requests,
      CustomUserDetails loginUser);

  void deleteSchedulesForPlanner(Long planId, List<Long> planIdList, CustomUserDetails loginUser);
}
