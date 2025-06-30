package com.ssafy.schedule.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.s3.service.S3Service;
import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.dto.ScheduleCreateRequestDto;
import com.ssafy.schedule.dto.ScheduleDto;
import com.ssafy.schedule.dto.ScheduleImage;
import com.ssafy.schedule.mapper.ScheduleMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleMapper scheduleMapper;
  private final PlannerMapper plannerMapper;
  private final S3Service s3Service;

  @Transactional
  @Override
  public void createSchedules(ScheduleCreateRequestDto request, CustomUserDetails loginUser) {
    Planner thisPlanner = plannerMapper.getPlannerById(request.plannerId());

    // 정렬: 모든 스케줄을 idx 순으로 정렬
    List<ScheduleDto> schedules = request.schedules();
    schedules.sort((o1, o2) -> o1.idx() - o2.idx());

    boolean isCorrect = false;
    for (int i = 0; i < schedules.size() - 1; i++) {
      // 검증 1: 날짜가 startDate, endDate 이내의 값인지 확인
      if (!thisPlanner.getStartDay().isAfter(schedules.get(i).date())
          && !thisPlanner.getEndDay().isBefore(schedules.get(i).date())) {
        // 검증 2: 날짜 순서가 맞는지 확인
        if (!schedules.get(i).date().isAfter(schedules.get(i+1).date())) {
          // 검증 3: 시간 순서가 맞는지 확인
          if (schedules.get(i).date().isEqual(schedules.get(i+1).date())
              && !schedules.get(i).startTime().isAfter(schedules.get(i+1).startTime())) {
            isCorrect = true;
          }
        }
      }
      if (!isCorrect) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            String.format("일정 검증 실패: idx %d 와 %d 사이의 날짜/시간 순서가 올바르지 않습니다.", i, i + 1)
        );
      }
    }

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

}
