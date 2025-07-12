package com.ssafy.planner.schedule.service;

import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.schedule.dto.ScheduleDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScheduleValidationService {

  public void validate(Planner planner, List<ScheduleDto> schedules) {
    if (schedules == null || schedules.isEmpty()) {
      return;
    }

    LocalDate startDate = planner.getStartDay();
    LocalDate endDate = planner.getEndDay();

    // 1) 전체 기간을 벗어났는지 확인
    for (ScheduleDto schedule : schedules) {
      if (schedule.date().isBefore(startDate)
          || schedule.date().isAfter(endDate)) {
        throw new IllegalArgumentException("스케줄이 플래너 기간을 벗어났습니다.");
      }
    }

    if (schedules.size() > 1) {
      // 2) 인접 스케줄 간 날짜, 순서 시간 순서 확인
      for (int i = 0; i < schedules.size() - 1; i++) {
        ScheduleDto cur = schedules.get(i);
        ScheduleDto next = schedules.get(i + 1);

        LocalDateTime curTime = LocalDateTime.of(cur.date(), cur.startTime());
        LocalDateTime nextTime = LocalDateTime.of(next.date(), next.startTime());

        if (curTime.isAfter(nextTime)) {
          throw new IllegalArgumentException(
              String.format("일정 검증 실패: idx %d 와 %d 사이의 날짜/시간 순서가 올바르지 않습니다.", i, i + 1)
          );
        }
      }
    }
  }
}