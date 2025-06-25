package com.ssafy.admin.filter;

import com.ssafy.admin.dto.ApiLogDto;
import com.ssafy.admin.service.ApiLogService;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.util.JWTUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

@RequiredArgsConstructor
@Slf4j
public class ApiLoggingFilter implements Filter {
    
    private final ApiLogService apiLogService;
    private final JWTUtil jwtUtil;
    private final MemberService memberService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        if ("OPTIONS".equals(req.getMethod())) {
            
            log.debug("[ApiLoggingFilter] 요청 실행됨");
            chain.doFilter(request, response);
            log.debug("[ApiLoggingFilter] 응답 실행됨");
            return;
        }
        
        // 캐싱 래퍼로 감싸기
        ContentCachingRequestWrapper wrappedReq = new ContentCachingRequestWrapper(req);
        
        // 시간 측정
        long start = System.currentTimeMillis();
        
        try {
            log.debug("[ApiLoggingFilter] 요청 실행됨");
            chain.doFilter(request, response);
            log.debug("[ApiLoggingFilter] 응답 실행됨");
        } finally {
            long duration = System.currentTimeMillis() - start;
            
            // 요청/응답 바디 읽기
            String requestBody = new String(wrappedReq.getContentAsByteArray(), StandardCharsets.UTF_8);
            
            // 로그 DTO 생성
            ApiLogDto dto = ApiLogDto.builder()
                    .memberId(resolveMemberId(wrappedReq))
                    .endpoint(wrappedReq.getRequestURI())
                    .httpMethod(wrappedReq.getMethod())
                    .statusCode(res.getStatus())
                    .responseTimeMs(duration)
                    .ipAddress(wrappedReq.getRemoteAddr())
                    .userAgent(wrappedReq.getHeader("User-Agent"))
                    .requestBody(truncateIfTooLong(requestBody))
                    .build();
            
            log.debug("API Log: {}", dto);
            apiLogService.createApiLog(dto);
        }
    }
    
    private Long resolveMemberId(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return null;
            }
            
            String token = authHeader.substring(7);
            Map<String, Object> claims = jwtUtil.getClaims(token);
            String email = (String) claims.get("email");
            
            Member member = memberService.getMemberByEmail(email);
            if (member == null) {
                return null;
            }
            return member.getId();
            
        } catch (Exception e) {
            log.warn("JWT 파싱 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }
    
    private String truncateIfTooLong(String input) {
        if (input == null) {
            return null;
        }
        return input.length() > 2000 ? input.substring(0, 2000) + "...(truncated)" : input;
    }
}
