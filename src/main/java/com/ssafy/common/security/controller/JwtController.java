package com.ssafy.common.security.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.controller.RestControllerHelper;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.util.JWTUtil;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class JwtController implements RestControllerHelper{

    private final JWTUtil jwtUtil;
    private final MemberService memberService;

    // TODO: 09-1. /api/auth/refresh요청 처리를 위한 handler 메서드를 살펴보자.
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
        }
        
        Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
        String email = (String) claims.get("email"); // Refresh Token 생성 시 넣었던 클레임 키

        if (email == null) {
            throw new JwtException("Invalid refresh token: email claim missing");
        }

        // 2. DB에서 사용자 조회 및 Refresh Token 일치 여부 확인
        Member member = memberService.getMemberByEmail(email);

        if (member == null || member.getRefreshToken() == null || !member.getRefreshToken().equals(refreshToken)) {
            log.warn("Invalid or mismatched refresh token for user: {}", email);
            // 보안: DB의 토큰과 불일치 시, 해당 사용자의 DB 토큰을 무효화(null 처리)하는 것도 고려
            // memberService.invalidateRefreshToken(email);
            return handleFail(new JwtException("Invalid refresh token"), HttpStatus.UNAUTHORIZED);
        }

        
        String newAccessToken = jwtUtil.createAccessToken(member);

       
        String newRefreshToken = jwtUtil.createRefreshToken(member);
        member.setRefreshToken(newRefreshToken); 
        memberService.updateMember(member);

        // 5. 새 토큰들을 응답으로 반환
        return handleSuccess(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Refresh-Token") String refreshToken) {
        if(refreshToken == null || refreshToken.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
        }
        
        Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
        String email = (String) claims.get("email");
        
        if(email == null) {
        	throw new JwtException("[유효하지 않는 토큰] 이메일이 존재하지 않습니다.");
        }
        
        Member member = memberService.getMemberByEmail(email);
        member.setRefreshToken(null);
        memberService.updateMember(member);
    	
        return handleSuccess(Map.of("accessToken", "", "refreshToken", ""));

        // END
    }
}
