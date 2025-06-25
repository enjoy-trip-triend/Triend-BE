package com.ssafy.admin.dto;

import java.time.LocalDateTime;

public record ApiLogDateRequestDto(
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    
    public ApiLogDateRequestDto {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("[ERROR] 시작 날짜는 종료 날짜보다 이전이어야 합니다.");
        }
    }
}
