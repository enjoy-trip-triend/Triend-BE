package com.ssafy.schedule.mapper;

import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.dto.ScheduleDto;
import com.ssafy.schedule.dto.ScheduleImage;
import com.ssafy.schedule.dto.ScheduleResponseDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScheduleMapper {
    
    int createSchedules(@Param("plannerId") Long plannerId, @Param("schedules") List<ScheduleDto> schedules);
    
    Schedule getScheduleById(Long scheduleId);
    
    List<ScheduleImage> findImagesByScheduleIds(@Param("planIds") List<Long> scheduleIds);
    
    int updateSchedule(Schedule schedule);
    
    int deleteSchedule(Long scheduleId);
    
    int deleteSchedulesByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay);
    
    int deleteSchedulesByPlanner(Long plannerId);
    
    List<ScheduleResponseDto> getSchedulesByPlanner(Long plannerId);
    
    void updateSchedulesBatch(List<Schedule> schedules);
    
    void deleteSchedulesBatch(List<Long> schedulesIdList);
    
    void insertScheduleImages(@Param("scheduleId") Long scheduleId,
            @Param("imageKeys") List<String> imageKeys);

    void updateScheduleDate(@Param("schedules") List<ScheduleResponseDto> schedules);
}
