package com.ssafy.planner.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerCreateRequest;
import com.ssafy.planner.dto.PlannerUpdateRequest;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.dto.ScheduleUpdateRequest;
import com.ssafy.schedule.mapper.ScheduleMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PlannerServiceImpl implements PlannerService {
    
    private final PlannerMapper plannerMapper;
    private final ScheduleMapper scheduleMapper;
    
    @Override
    public void createPlanner(PlannerCreateRequest request, CustomUserDetails loginUser) {
        
        if (request.startDay()
                .isAfter(request.endDay())) {
            throw new RuntimeException("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
        }
        
        Planner planner = Planner.builder()
                .name(request.name())
                .startDay(request.startDay())
                .endDay(request.endDay())
                .member(loginUser.getMember())
                .location(request.location())
                .build();
        
        int cnt = plannerMapper.createPlanner(planner);
        
        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 플래너 생성 실패");
        }
    }
    
    @Override
    public List<Planner> getPlannersByMember(CustomUserDetails loginUser) {
        List<Planner> planners = plannerMapper.getPlannersByMember(loginUser.getMember()
                .getId());
        
        if (planners == null) {
            throw new RuntimeException("[ERROR] 플래너가 존재하지 않습니다.");
        }
        
        return planners;
    }
    
    @Override
    public Planner getPlannerById(Long plannerId, CustomUserDetails loginUser) {
        Planner planner = plannerMapper.getPlannerById(plannerId);
        
        if (planner == null) {
            throw new RuntimeException("[ERROR] 해당 ID의 플래너가 존재하지 않습니다.");
        }
        if (planner.getMember()
                .getId() != loginUser.getMember()
                .getId()) {
            throw new RuntimeException("[ERROR] 사용자가 일치하지 않습니다.");
        }
        
        return plannerMapper.getPlannerById(plannerId);
    }
    
    @Override
    public void updatePlanner(Long plannerId, PlannerUpdateRequest request,
            CustomUserDetails loginUser) {
        Planner targetPlanner = plannerMapper.getPlannerById(plannerId);
        
        if (targetPlanner == null) {
            throw new RuntimeException("[ERROR] 해당 ID의 플래너가 존재하지 않습니다.");
        }
        
        if (targetPlanner.getMember()
                .getId() != loginUser.getMember()
                .getId()) {
            throw new RuntimeException("[ERROR] 사용자가 일치하지 않습니다.");
        }
        
        // 날짜 변경시 해당 날짜의 플랜 삭제
        if (request.startDay()
                .isAfter(request.endDay())) {
            throw new RuntimeException("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
        }
        
        // 바뀐 날짜에 포함되지 않는 날짜에 해당하는 계획 삭제
        scheduleMapper.deleteSchedulesByPlannerAndDate(plannerId, request.startDay(),
                request.endDay());
        
        Planner planner = Planner.builder()
                .id(request.id())
                .name(request.name())
                .startDay(request.startDay())
                .endDay(request.endDay())
                .member(loginUser.getMember())
                .location(request.location())
                .build();
        
        int cnt = plannerMapper.updatePlanner(planner);
        log.info("cnt : {}", cnt);
        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 플래너 수정 실패");
        }
    }
    
    @Override
    public void deletePlanner(Long plannerId, CustomUserDetails loginUser) {
        scheduleMapper.deleteSchedulesByPlanner(plannerId);
        
        int cnt = plannerMapper.deletePlanner(plannerId);
        
        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 플래너 삭제 실패");
        }
    }
    
    @Override
    public List<Schedule> getSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser) {
        
        Planner planner = plannerMapper.getPlannerById(plannerId);
        
        if (planner.getMember()
                .getId() != loginUser.getMember()
                .getId()) {
            throw new RuntimeException("[ERROR] 사용자가 다릅니다.");
        }
        
        List<Schedule> schedules = scheduleMapper.getSchedulesByPlanner(plannerId);
        
        if (schedules == null) {
            throw new RuntimeException("[ERROR] 플래너가 존재하지 않습니다.");
        }
        
        return schedules;
    }
    
    @Override
    public List<Schedule> getSchedulesByShared(Long plannerId) {
        List<Schedule> schedules = scheduleMapper.getSchedulesByPlanner(plannerId);
        if (schedules == null) {
            throw new RuntimeException("[ERROR] 플래너가 존재하지 않습니다.");
        }
        return schedules;
    }
    
    @Override
    public void updateSchedulesForPlanner(Long plannerId, List<ScheduleUpdateRequest> requests,
            CustomUserDetails loginUser) {
        
        Planner planner = plannerMapper.getPlannerById(plannerId);
        if (planner == null) {
            throw new RuntimeException("[ERROR] 해당 ID의 플래너가 존재하지 않습니다.");
        }
        
        Collections.sort(requests, (o1, o2) -> {
            if (o1.date()
                    .isBefore(o2.date())) {
                return -1;
            } else if (o1.date()
                    .isAfter(o2.date())) {
                return 1;
            } else {
                return o1.startTime()
                        .compareTo(o2.startTime());
            }
        });
        
        boolean isFirst = true;
        LocalDate preDate = null;
        LocalTime preStartTime = null;
        LocalTime preEndTime = null;
        for (ScheduleUpdateRequest request : requests) {
            
            if (request.startTime()
                    .equals(request.endTime())) {
                throw new RuntimeException("[ERROR] 시작 시간과 종료 시간이 같습니다.");
            }
            
            if (request.startTime()
                    .isAfter(request.endTime())) {
                throw new RuntimeException("[ERROR] 시작 시간이 종료 시간보다 늦을 수 없습니다.");
            }
            
            if (isFirst) {
                isFirst = false;
                preDate = request.date();
                preStartTime = request.startTime();
                preEndTime = request.endTime();
                continue;
            }
            
            if (request.date()
                    .isEqual(preDate)) {
                if (request.startTime()
                        .equals(preStartTime)) {
                    throw new RuntimeException("[ERROR] 같은 날짜에 시작 시간이 중복된 일정이 존재합니다.");
                }
                
                if (request.startTime()
                        .isBefore(preEndTime)) {
                    throw new RuntimeException("[ERROR] 같은 날짜에 겹치는 일정이 존재합니다.");
                }
            }
            
            preDate = request.date();
            preStartTime = request.startTime();
            preEndTime = request.endTime();
        }
        
        List<Schedule> schedules = requests.stream()
                .map(request -> toEntity(request, planner))
                .collect(Collectors.toList());
        scheduleMapper.updateSchedulesBatch(schedules);
    }
    
    public void deleteSchedulesForPlanner(Long plannerId, List<Long> planIdList,
            CustomUserDetails loginUser) {
        Planner planner = plannerMapper.getPlannerById(plannerId);
        
        if (planner.getMember()
                .getId() != loginUser.getMember()
                .getId()) {
            throw new RuntimeException("[ERROR] 사용자가 다릅니다.");
        }
        
        scheduleMapper.deleteSchedulesBatch(planIdList);
    }
    
    public Schedule toEntity(ScheduleUpdateRequest request, Planner planner) {
        return Schedule.builder()
                .id(request.id())
                .date(request.date())
                .startTime(request.startTime())
                .placeId(request.placeId())
                .content(request.content())
                .plannerId(planner.getId())
                .placeUrl(request.placeUrl())
                .build();
    }
}
