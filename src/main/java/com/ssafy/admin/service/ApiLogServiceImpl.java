package com.ssafy.admin.service;

import com.ssafy.admin.dto.ApiLogDto;
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
    public List<ApiLogDto> getApiLogsByDateRange(LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        List<ApiLogDto> apiLogDtoList = apiLogMapper.selectApiLogsByDateRange(startDateTime, endDateTime);
        log.debug("Retrieved API logs: {}", apiLogDtoList);
        
        return apiLogDtoList;
    }
    
    @Override
    public List<ApiLogDto> getApiLogsByMember(Long memberId) {
        return apiLogMapper.selectApiLogsByMember(memberId);
    }
}
