package com.ssafy.schedule.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.s3.service.S3Service;
import com.ssafy.schedule.dto.ScheduleCreateRequestDto;
import com.ssafy.schedule.dto.ScheduleDto;
import com.ssafy.schedule.dto.ScheduleImage;
import com.ssafy.schedule.dto.ScheduleOrderDto;
import com.ssafy.schedule.dto.ScheduleOrderUpdateRequestDto;
import com.ssafy.schedule.dto.ScheduleResponseDto;
import com.ssafy.schedule.mapper.ScheduleMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    
    private final ScheduleValidationService scheduleValidationService;
    private final ScheduleMapper scheduleMapper;
    private final PlannerMapper plannerMapper;
    private final S3Service s3Service;
    
    @Transactional
    @Override
    public void createSchedules(ScheduleCreateRequestDto request, CustomUserDetails loginUser) {
        Planner planner = plannerMapper.getPlannerById(request.plannerId());
        List<ScheduleDto> schedules = request.schedules();
        
        // 도메인 서비스에서 검증
        scheduleValidationService.validate(planner, schedules);
        
        // 검증 통과 후 insert
        int cnt = scheduleMapper.createSchedules(request.plannerId(), request.schedules());
        if (cnt != request.schedules()
                .size()) {
            throw new RuntimeException("[ERROR] 스케줄 추가 실패");
        }
    }
    
    @Override
    public void deleteSchedule(Long planId, CustomUserDetails loginUser) {
        int cnt = scheduleMapper.deleteSchedule(planId);
        
        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 스케줄 삭제 실패");
        }
    }
    
    @Override
    public void deleteSchedulesByPlannerAndDate(Long plannerId, LocalDate startDay,
            LocalDate endDay) {
        int cnt = scheduleMapper.deleteSchedulesByPlannerAndDate(plannerId, startDay, endDay);
        
        if (cnt < 1) {
            throw new RuntimeException("[ERROR] 스케줄 삭제 실패");
        }
    }
    
    @Override
    public void deleteSchedulesByPlanner(Long plannerId) {
        int cnt = scheduleMapper.deleteSchedulesByPlanner(plannerId);
        
        if (cnt < 1) {
            throw new RuntimeException("[ERROR] 스케줄 삭제 실패");
        }
    }
    
    public Map<Long, List<String>> getPresignedImageUrlsByScheduleIds(List<Long> planIds) {
        
        List<ScheduleImage> allImages = scheduleMapper.findImagesByScheduleIds(planIds);
        
        Map<Long, List<String>> result = new HashMap<>();
        for (ScheduleImage image : allImages) {
            if (image == null || image.getPlanId() == null || image.getImageKey() == null) {
                continue;
            }
            
            String presignedUrl = s3Service.generatePresignedGetUrl(image.getImageKey());
            
            result.computeIfAbsent(image.getPlanId(), k -> new ArrayList<>())
                    .add(presignedUrl);
        }
        
        return result;
    }
    
    @Override
    public void updateScheduleOrder(ScheduleOrderUpdateRequestDto request, Long plannerId, CustomUserDetails loginUser) {
        Planner planner = plannerMapper.getPlannerById(plannerId);
        
        if (planner == null) {
            throw new IllegalArgumentException("플래너가 존재하지 않습니다.");
        }
        
        List<ScheduleResponseDto> schedules = scheduleMapper.getSchedulesByPlannerAndDate(plannerId, request.date());
        if (schedules == null || schedules.isEmpty()) {
            throw new IllegalArgumentException("플래너에 스케줄이 존재하지 않습니다.");
        }
        
        List<ScheduleOrderDto> reorderSchedules = request.schedules();
        reorderSchedules.sort(Comparator.comparingLong(ScheduleOrderDto::idx));
        
        if (schedules.size() != reorderSchedules.size()) {
            throw new IllegalArgumentException("스케줄의 개수가 일치하지 않습니다.");
        }
        
        // idx로 정렬 후 새로운 순서 매핑
        List<ScheduleOrderDto> newOrder = new ArrayList<>();
        for (int i = 0; i < reorderSchedules.size(); i++) {
            ScheduleOrderDto orderDto = reorderSchedules.get(i);
            ScheduleResponseDto schedule = schedules.get(i);
            
            newOrder.add(new ScheduleOrderDto(
                    orderDto.scheduleId(),
                    schedule.getIdx()
            ));
        }
        scheduleMapper.updateScheduleOrder(newOrder);
    }
}
