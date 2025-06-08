package com.ssafy.common.security.filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, MemberService memberService, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
        this.setFilterProcessesUrl("/api/auth/login"); // 로그인 URL 설정
        this.setUsernameParameter("email"); // email을 username으로 사용
        this.setPasswordParameter("password"); // password를 password로 사용
    }

    // 로그인 성공 시 실행하는 메소드 (JWT 발급)
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) {

    	CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
    	String accessToken = jwtUtil.createAccessToken(details.getMember());
    	String refreshToken = jwtUtil.createRefreshToken(details.getMember());
    	
    	Member member = details.getMember();
    	member.setRefreshToken(refreshToken);
    	memberService.updateMember(member);
    	
    	Map<String, String> result = Map.of("status", "SUCCESS", "accessToken", accessToken, "refreshToken", refreshToken);
    	handleResult(response, result, HttpStatus.OK);
    }

    // 로그인 실패 시 실행하는 메소드
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) {

        throw failed;
    }

    // 결과 전송 helper 메소드
    private void handleResult(HttpServletResponse response, Map<String, ?> data, HttpStatus status) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(data);
            response.setStatus(status.value());
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            log.error("Error writing JSON response", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
