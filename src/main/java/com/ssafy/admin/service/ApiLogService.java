package com.ssafy.admin.service;

import com.ssafy.admin.dto.ApiLogCountResponseDto;
import com.ssafy.admin.dto.ApiLogDto;
import com.ssafy.admin.dto.ApiLogLatencyResponseDto;
import com.ssafy.admin.dto.ApiLogResponseDto;
import com.ssafy.admin.dto.ApiLogStatusRateResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ApiLogService {
    
    /**
     * API 로그를 생성합니다.
     */
    void createApiLog(ApiLogDto apiLogDto);
    
    /**
     * 날짜 범위로 API 로그를 조회합니다.
     */
    List<ApiLogResponseDto> getApiLogs(LocalDateTime from, LocalDateTime to, Long memberId);
    
    /**
     * 각 API 호출 개수 통계를 조회합니다.
     */
    List<ApiLogCountResponseDto> getApiLogCount(LocalDateTime from, LocalDateTime to, Long memberId);
    
    /**
     * 각 API 상태 코드 비율 통계를 조회합니다.
     */
    List<ApiLogStatusRateResponseDto> getApiLogStatus(LocalDateTime from, LocalDateTime to);
    
    /**
     * 각 API 호출의 평균 응답 시간을 조회합니다.
     */
    List<ApiLogLatencyResponseDto> getApiLogLatency(LocalDateTime from, LocalDateTime to);
}
