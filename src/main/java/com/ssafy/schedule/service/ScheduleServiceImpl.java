package com.ssafy.schedule.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.s3.service.S3Service;
import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.dto.ScheduleCreateRequest;
import com.ssafy.schedule.dto.ScheduleImage;
import com.ssafy.schedule.mapper.ScheduleMapper;
import java.time.LocalDate;
import java.util.ArrayList;
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
    
    private final ScheduleMapper scheduleMapper;
    private final PlannerMapper plannerMapper;
    private final S3Service s3Service;
    
    /**
     * 기존에는 schedule 추가할 때, 한 개씩 추가하고, 이전 schedules 불러와서 시간 중복 체크한 후 생성했는데 이제는 여러 개를 한 번에 추가할 수 있도록
     * 변경해야 되고, 검증 로직도 변경해야 합니다.
     */
    @Override
    public Long createSchedule(ScheduleCreateRequest request, CustomUserDetails loginUser) {

//        List<Schedule> schedules = scheduleMapper.getSchedulesByPlanner(request.plannerId());
        
        // idx로 정렬한 후, startTime 검증
//		Collections.sort(schedules, (o1, o2) -> {
//			if (o1.getDate().isBefore(o2.getDate())) {
//				return -1;
//			} else if (o1.getDate().isAfter(o2.getDate())) {
//				return 1;
//			} else {
//				if (o1.getStartTime().equals(o2.getStartTime())) {
//					return o1.getEndTime().compareTo(o2.getEndTime());
//				} else {
//					return o1.getStartTime().compareTo(o2.getStartTime());
//				}
//			}
//		});
//
//		for (Schedule schedule : schedules) {
//			if (request.date().isEqual(schedule.getDate())) {
//				if (!(request.endTime().isBefore(schedule.getStartTime()) || request.startTime().isAfter(schedule.getEndTime()))) {
//					throw new RuntimeException("[ERROR] 같은 날짜에 시작 시간이 중복된 일정이 존재합니다." +
//							" 시작 시간: " + request.startTime() + ", 종료 시간: " + request.endTime() +
//							", 기존 일정 시작 시간: " + schedule.getStartTime() + ", 종료 시간: " + schedule.getEndTime());
//				}
//			}
//			if(schedule.getDate().isAfter(request.date())) {
//				break;
//			}
//
//		}
        
        Planner planner = plannerMapper.getPlannerById(request.plannerId());
        
        Schedule schedule = Schedule.builder()
                .date(request.date())
                .startTime(request.startTime())
                .placeId(request.placeId())
                .content(request.content())
                .placeUrl(request.placeUrl())
                .idx((request.idx()))
                .plannerId(planner.getId())
                .build();
        
        int cnt = scheduleMapper.createSchedule(schedule);
        
        if (cnt != 1) {
            throw new RuntimeException("[ERROR]  생성 실패");
        }
        
        return schedule.getId();
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
    
}
