package com.ssafy.planner.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.dto.PlannerCreateRequestDto;
import com.ssafy.planner.dto.PlannerLocationDto;
import com.ssafy.planner.dto.PlannerUpdateRequestDto;
import com.ssafy.planner.mapper.PlannerLocationMapper;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.planner.plannershare.mapper.PlannerMemberMapper;
import com.ssafy.planner.schedule.dto.ScheduleRequestDto;
import com.ssafy.planner.schedule.dto.ScheduleResponseDto;
import com.ssafy.planner.schedule.mapper.ScheduleMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PlannerServiceImpl implements PlannerService {

    private final PlannerMapper plannerMapper;
    private final ScheduleMapper scheduleMapper;
    private final PlannerMemberMapper plannerMemberMapper;
    private final PlannerLocationMapper plannerLocationMapper;

    @Override
    public void createPlanner(PlannerCreateRequestDto request, CustomUserDetails loginUser) {

        if (request.startDay()
                .isAfter(request.endDay())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "시작일이 종료일보다 늦을 수 없습니다.");
        }

        Planner planner = Planner.builder()
                .startDay(request.startDay())
                .endDay(request.endDay())
                .memberId(loginUser.getMember()
                        .getId())
                .name(request.name())
                .comment(request.comment())
                .exposure(request.exposure())
                .build();

        int cnt = plannerMapper.createPlanner(planner); // 작업 행 개수 반환 (성공시 1)
        plannerMemberMapper.insertPlannerMember(planner.getId(), loginUser.getMember()
                .getId());
        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 플래너 생성 실패");
        }

        if (request.locations() != null && !request.locations()
                .isEmpty()) // 추가할 장소가 존재하는 경우에만 추가
        {
            plannerLocationMapper.insertLocation(planner.getId(), request.locations());
        }
    }

    @Override
    public List<Planner> getPlannersByMember(CustomUserDetails loginUser) {
        List<Planner> planners = plannerMapper.getPlannersByMemberId(loginUser.getMember()
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
        if (!Objects.equals(planner.getMemberId(), loginUser.getMember()
                .getId())) {
            throw new RuntimeException("[ERROR] 사용자가 일치하지 않습니다.");
        }

        return planner;
    }

    @Override
    public void updatePlanner(Long plannerId, PlannerUpdateRequestDto request,
            CustomUserDetails loginUser) {
        Planner targetPlanner = plannerMapper.getPlannerById(plannerId);

        if (targetPlanner == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 플래너가 존재하지 않습니다.");
        }

        if (!Objects.equals(targetPlanner.getMemberId(), loginUser.getMember()
                .getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사용자가 일치하지 않습니다.");
        }

        // 날짜 변경시 해당 날짜의 플랜 삭제
        if (request.startDay()
                .isAfter(request.endDay())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "시작일이 종료일보다 늦을 수 없습니다.");
        }

        Planner planner = Planner.builder()
                .id(plannerId)
                .startDay(request.startDay())
                .endDay(request.endDay())
                .memberId(loginUser.getMember()
                        .getId())
                .name(request.name())
                .comment(request.comment())
                .exposure(request.exposure())
                .build();

        int cnt = plannerMapper.updatePlanner(planner);
        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 플래너 수정 실패");
        }

        plannerLocationMapper.deleteLocation(plannerId);
        if (request.locations() != null && !request.locations()
                .isEmpty()) {
            plannerLocationMapper.insertLocation(plannerId, request.locations());
        }
    }

    @Override
    public void deletePlanner(Long plannerId, CustomUserDetails loginUser) {
        Planner planner = plannerMapper.getPlannerById(plannerId);

        if (!planner.getMemberId()
                .equals(loginUser.getMember()
                        .getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }

        int cnt = plannerMapper.deletePlanner(plannerId);

        if (cnt != 1) {
            throw new RuntimeException("[ERROR] 플래너 삭제 실패");
        }
    }

    @Override
    public List<ScheduleResponseDto> getSchedulesByShared(Long plannerId) {
        List<ScheduleResponseDto> schedules = scheduleMapper.getSchedulesByPlanner(plannerId);
        if (schedules == null) {
            throw new RuntimeException("[ERROR] 플래너가 존재하지 않습니다.");
        }
        return schedules;
    }

    @Override
    public List<PlannerLocationDto> getLocationsById(Long plannerId, CustomUserDetails loginUser) {
        Planner planner = plannerMapper.getPlannerById(plannerId);

        if (!planner.getMemberId()
                .equals(loginUser.getMember()
                        .getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }
        return plannerLocationMapper.findByPlannerId(plannerId);
    }
}
