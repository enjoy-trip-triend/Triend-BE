package com.ssafy.admin.mapper;

import com.ssafy.admin.dto.ApiLogCountResponseDto;
import com.ssafy.admin.dto.ApiLogDto;
import com.ssafy.admin.dto.ApiLogLatencyResponseDto;
import com.ssafy.admin.dto.ApiLogResponseDto;
import com.ssafy.admin.dto.ApiLogStatusRateResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApiLogMapper {
    
    void insertApiLog(ApiLogDto apiLogDto);
    
    List<ApiLogResponseDto> selectApiLogs(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
    
    List<ApiLogResponseDto> selectApiLogsByMember(Long memberId);
    
    List<ApiLogCountResponseDto> selectApiLogCount(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("memberId") Long memberId);
    
    List<ApiLogStatusRateResponseDto> selectApiLogStatusRate(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
    
    List<ApiLogLatencyResponseDto> selectApiLogLatency(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}

