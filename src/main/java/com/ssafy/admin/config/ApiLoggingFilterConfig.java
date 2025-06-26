package com.ssafy.admin.config;

import com.ssafy.admin.filter.ApiLoggingFilter;
import com.ssafy.admin.service.ApiLogService;
import com.ssafy.member.service.MemberService;
import com.ssafy.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApiLoggingFilterConfig {
    
    private static final int API_LOGGING_FILTER_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER - 1;
    
    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> apiLoggingFilter(ApiLogService apiLogService, JWTUtil jwtUtil, MemberService memberService) {
        FilterRegistrationBean<ApiLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new ApiLoggingFilter(apiLogService, jwtUtil, memberService));
        
        // 순서 맨 앞으로 적용
        registrationBean.setOrder(API_LOGGING_FILTER_ORDER);
        
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}

