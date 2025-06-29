package com.ssafy.admin.service;

import com.ssafy.admin.dto.ApiLogDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ApiLogService {
    
    /**
     * API 로그를 생성합니다.
     */
    void createApiLog(ApiLogDto apiLogDto);
    
    
    /**
     * 유저별 API 로그를 조회합니다.
     */
    List<ApiLogDto> getApiLogsByMember(Long memberId);
    
    /**
     * 날짜 범위로 API 로그를 조회합니다.
     */
    List<ApiLogDto> getApiLogsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime);
    
}
