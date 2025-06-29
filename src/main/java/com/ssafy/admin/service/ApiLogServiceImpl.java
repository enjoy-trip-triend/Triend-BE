package com.ssafy.admin.service;

import com.ssafy.admin.dto.ApiLogCountResponseDto;
import com.ssafy.admin.dto.ApiLogDto;
import com.ssafy.admin.dto.ApiLogLatencyResponseDto;
import com.ssafy.admin.dto.ApiLogResponseDto;
import com.ssafy.admin.dto.ApiLogStatusRateResponseDto;
import com.ssafy.admin.mapper.ApiLogMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiLogServiceImpl implements ApiLogService {
    
    private final ApiLogMapper apiLogMapper;
    
    @Override
    @Transactional
    public void createApiLog(ApiLogDto apiLogDto) {
        apiLogMapper.insertApiLog(apiLogDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ApiLogResponseDto> getApiLogs(LocalDateTime from, LocalDateTime to) {
        List<ApiLogResponseDto> apiLogDtoList = apiLogMapper.selectApiLogs(from, to);
        log.debug("Retrieved API logs: {}", apiLogDtoList);
        
        return apiLogDtoList;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ApiLogResponseDto> getApiLogsByMember(Long memberId) {
        return apiLogMapper.selectApiLogsByMember(memberId);
    }
    
    @Override
    @Transactional
    public List<ApiLogCountResponseDto> getApiLogCount(LocalDateTime from, LocalDateTime to, Long memberId) {
        return apiLogMapper.selectApiLogCount(from, to, memberId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ApiLogStatusRateResponseDto> getApiLogStatus(LocalDateTime from, LocalDateTime to) {
        return apiLogMapper.selectApiLogStatusRate(from, to);
    }
    
    @Override
    public List<ApiLogLatencyResponseDto> getApiLogLatency(LocalDateTime from, LocalDateTime to) {
        return apiLogMapper.selectApiLogLatency(from, to);
    }
}
