package com.ssafy.schedule.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.s3.service.S3Service;
import com.ssafy.schedule.dto.ScheduleRequestDto;
import com.ssafy.schedule.dto.ScheduleDto;
import com.ssafy.schedule.dto.ScheduleImage;
import com.ssafy.schedule.dto.ScheduleResponseDto;
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

  private final ScheduleValidationService scheduleValidationService;
  private final ScheduleMapper scheduleMapper;
  private final PlannerMapper plannerMapper;
  private final S3Service s3Service;

  @Transactional
  @Override
  public void createSchedules(ScheduleRequestDto request, CustomUserDetails loginUser) {
    Planner planner = plannerMapper.getPlannerById(request.plannerId());

    // 기존 스케줄이 있으면 모두 delete
    List<ScheduleResponseDto> originSchedules = scheduleMapper.getSchedulesByPlanner(request.plannerId());
    if (originSchedules != null && !originSchedules.isEmpty()) {
      scheduleMapper.deleteSchedulesByPlanner(request.plannerId());
    }

    // 새로운 스케줄
    List<ScheduleDto> newSchedules = request.schedules();

    // 도메인 서비스에서 검증
    newSchedules.sort((o1, o2) -> o1.idx() - o2.idx());
    scheduleValidationService.validate(planner, newSchedules);

    // 검증 통과 후 insert
    int cnt = scheduleMapper.createSchedules(request.plannerId(), request.schedules());
    if (cnt != request.schedules().size()) {
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
  public void deleteSchedulesByPlanner(Long plannerId, CustomUserDetails loginUser) {
    if (!plannerMapper.getPlannerById(plannerId).getMemberId()
        .equals(loginUser.getMember().getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사용자가 일치하지 않습니다.");
    }

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
