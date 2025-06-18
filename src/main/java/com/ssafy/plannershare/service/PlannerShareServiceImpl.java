package com.ssafy.plannershare.service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.plannershare.dto.PlannerShare;
import com.ssafy.plannershare.mapper.PlannerShareMapper;
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
}
