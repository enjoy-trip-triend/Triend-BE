package com.ssafy.admin.mapper;

import com.ssafy.admin.dto.ApiLogDto;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApiLogMapper {
    
    void insertApiLog(ApiLogDto apiLogDto);
    
    List<ApiLogDto> selectAllApiLogs();
    
    List<ApiLogDto> selectApiLogsByMember(Long memberId);
    
    List<ApiLogDto> selectApiLogsByDateRange(@Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);
    
}
