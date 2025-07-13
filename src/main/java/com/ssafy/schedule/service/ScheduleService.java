package com.ssafy.schedule.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.schedule.dto.ScheduleCreateRequestDto;
import com.ssafy.schedule.dto.ScheduleOrderUpdateRequestDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ScheduleService {
    
    void createSchedules(ScheduleCreateRequestDto request, CustomUserDetails loginUser);
    
    void deleteSchedule(Long planId, CustomUserDetails loginUser);
    
    void deleteSchedulesByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay);
    
    void deleteSchedulesByPlanner(Long plannerId);
    
    Map<Long, List<String>> getPresignedImageUrlsByScheduleIds(List<Long> planIds);
    
    void updateScheduleOrder(ScheduleOrderUpdateRequestDto request, Long plannerId, CustomUserDetails loginUser);
}
