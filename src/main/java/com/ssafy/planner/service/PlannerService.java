package com.ssafy.planner.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerCreateRequest;
import com.ssafy.planner.dto.PlannerUpdateRequest;
import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.dto.ScheduleUpdateRequest;
import java.util.List;

public interface PlannerService {
    
    void createPlanner(PlannerCreateRequest request, CustomUserDetails loginUser);
    
    List<Planner> getPlannersByMember(CustomUserDetails loginUser);
    
    List<Schedule> getSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser);
    
    List<Schedule> getSchedulesByShared(Long plannerId);
    
    Planner getPlannerById(Long plannerId, CustomUserDetails loginUser);
    
    void updatePlanner(Long plannerId, PlannerUpdateRequest request, CustomUserDetails loginUser);
    
    void deletePlanner(Long plannerId, CustomUserDetails loginUser);
    
    void updateSchedulesForPlanner(Long plannerId, List<ScheduleUpdateRequest> requests,
            CustomUserDetails loginUser);
    
    void deleteSchedulesForPlanner(Long planId, List<Long> planIdList, CustomUserDetails loginUser);
}
