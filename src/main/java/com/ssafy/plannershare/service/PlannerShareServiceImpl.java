package com.ssafy.plannershare.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.planner.dto.Planner;
import com.ssafy.planner.mapper.PlannerMapper;
import com.ssafy.plannershare.dto.PlannerShare;
import com.ssafy.plannershare.dto.PlannerShareResponseDto;
import com.ssafy.plannershare.mapper.PlannerMemberMapper;
import com.ssafy.plannershare.mapper.PlannerShareMapper;
import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.mapper.ScheduleMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PlannerShareServiceImpl implements PlannerShareService{
    private final PlannerShareMapper plannerShareMapper;
    private final PlannerMemberMapper plannerMemberMapper;
    private final PlannerMapper plannerMapper;
    private final ScheduleMapper scheduleMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public String createSecreteCode(Long plannerId, CustomUserDetails loginUser, String rawPassword) {
        // 비밀번호 해시
        String hashedPassword = passwordEncoder.encode(rawPassword);
        // 시크릿 코드 생성
        String secretCode = UUID.randomUUID().toString().substring(0, 10);
        PlannerShare share = PlannerShare.builder()
                .secretCode(secretCode)
                .password(hashedPassword) // 이제 변수명 통일 했으니까 password!
                .plannerId(plannerId)
                .build();
        plannerShareMapper.insertPlannerShare(share);
        return secretCode;
    }

    @Transactional
    @Override
    public void verifyPassword(String secretCode, String inputPassword) {
        PlannerShare share = plannerShareMapper.findBySecretCode(secretCode);
        if(share==null){
            throw new IllegalArgumentException("공유 링크가 존재하지 않습니다.");
        }
        boolean matches = passwordEncoder.matches(inputPassword, share.getPassword());
        if (!matches) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    @Override
    public PlannerShareResponseDto getSharedPlanner(String secretCode, CustomUserDetails loginUser) {
        PlannerShare share = plannerShareMapper.findBySecretCode(secretCode);
        if (share == null) {
            throw new IllegalArgumentException("공유 링크가 존재하지 않습니다.");
        }
        Long plannerId = share.getPlannerId();
        Planner planner = plannerMapper.getPlannerById(plannerId);
        List<Schedule> scheduleList = scheduleMapper.getSchedulesByPlanner(plannerId);
        boolean isEditable = false;
        if (loginUser != null) {
            if (planner.getMember().getId().equals(loginUser.getMember().getId())) {
                isEditable = true;
            } else {
                boolean isMember = plannerMemberMapper.isPlannerMember(plannerId, loginUser.getMember().getId());
                if (isMember) {
                    isEditable = true;
                }
            }
        }

        return new PlannerShareResponseDto(planner, scheduleList, isEditable);
    }

    @Transactional
    @Override
    public void addMemberToPlannerMember(String secretCode, CustomUserDetails loginUser) {
        PlannerShare share = plannerShareMapper.findBySecretCode(secretCode);
        if (share == null) {
            throw new IllegalArgumentException("공유 링크가 존재하지 않습니다.");
        }

        Long plannerId = share.getPlannerId();
        Long memberId = loginUser.getMember().getId();

        boolean isMember = plannerMemberMapper.isPlannerMember(plannerId, memberId);

        if (!isMember) {
            plannerMemberMapper.insertPlannerMember(plannerId, memberId);
        }
    }
}
