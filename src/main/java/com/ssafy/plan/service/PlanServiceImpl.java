package com.ssafy.plan.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plan.dto.Plan;
import com.ssafy.plan.dto.PlanCreateRequest;
import com.ssafy.plan.dto.PlanImage;
import com.ssafy.plan.mapper.PlanMapper;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class PlanServiceImpl implements PlanService {
	
	private final PlanMapper planMapper;
	private final PlannerMapper plannerMapper;
	private final S3Service s3Service;
	@Override
	public Long createPlan(PlanCreateRequest request, CustomUserDetails loginUser) {
		
		if(request.startTime().isAfter(request.endTime())) {
			throw new RuntimeException("[ERROR] 시작 시간이 종료 시간보다 늦을 수 없습니다.");
		}
		
		List<Plan> plans = planMapper.getPlansByPlanner(request.plannerId());
		
		Collections.sort(plans, (o1, o2) -> {
			if (o1.getDate().isBefore(o2.getDate())) {
				return -1;
			} else if (o1.getDate().isAfter(o2.getDate())) {
				return 1;
			} else {
				if (o1.getStartTime().equals(o2.getStartTime())) {
					return o1.getEndTime().compareTo(o2.getEndTime());
				} else {
					return o1.getStartTime().compareTo(o2.getStartTime());
				}
			}
		});
		
		for (Plan plan: plans) {
			if (request.date().isEqual(plan.getDate())) {
				if (!(request.endTime().isBefore(plan.getStartTime()) || request.startTime().isAfter(plan.getEndTime()))) {
					throw new RuntimeException("[ERROR] 같은 날짜에 시작 시간이 중복된 일정이 존재합니다." + 
							" 시작 시간: " + request.startTime() + ", 종료 시간: " + request.endTime() + 
							", 기존 일정 시작 시간: " + plan.getStartTime() + ", 종료 시간: " + plan.getEndTime());
				}
			}
			if(plan.getDate().isAfter(request.date())) {
				break; 
			}

		}
		
		
		Planner planner = plannerMapper.getPlannerById(request.plannerId());
		
		Plan plan = Plan.builder()
				.date(request.date())
				.startTime(request.startTime())
				.endTime(request.endTime())
				.placeName(request.placeName())
				.address(request.address())
				.content(request.content())
				.lat(request.lat())
				.lon(request.lon())
				.placeUrl(request.placeUrl())
				.planner(planner)
				.build();
		
		int cnt = planMapper.createPlan(plan);

		if (cnt != 1) {
			throw new RuntimeException("[ERROR] 플랜 생성 실패");
		}
		
		return plan.getId();
	}

	@Override
	public void deletePlan(Long planId, CustomUserDetails loginUser) {
		int cnt = planMapper.deletePlan(planId);
		
		if (cnt != 1) {
			throw new RuntimeException("[ERROR] 플랜 삭제 실패");
		}
	}
	
	@Override
	public void deletePlansByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay) {
		int cnt = planMapper.deletePlansByPlannerAndDate(plannerId, startDay, endDay);
		
		if (cnt < 1) {
			throw new RuntimeException("[ERROR] 플랜 삭제 실패");
		}
	}
	
	@Override
	public void deletePlansByPlanner(Long plannerId) {
		int cnt = planMapper.deletePlansByPlanner(plannerId);
		
		if (cnt < 1) {
			throw new RuntimeException("[ERROR] 플랜 삭제 실패");
		}
	}
	
    public Map<Long, List<String>> getPresignedImageUrlsByPlanIds(List<Long> planIds) {
        for(Long planId : planIds) {
			System.out.println(planId);
		}
    	List<PlanImage> allImages = planMapper.findImagesByPlanIds(planIds);
        
        Map<Long, List<String>> result = new HashMap<>();
        for (PlanImage image : allImages) {
            if (image == null || image.getPlanId() == null || image.getImageKey() == null) continue;

            String presignedUrl = s3Service.generatePresignedGetUrl(image.getImageKey());
            
            result.computeIfAbsent(image.getPlanId(), k -> new ArrayList<>())
                  .add(presignedUrl);
        }

        return result;
    }

}
