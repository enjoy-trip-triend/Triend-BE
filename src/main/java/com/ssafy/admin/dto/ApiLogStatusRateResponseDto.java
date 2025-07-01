package com.ssafy.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogStatusRateResponseDto {
    
    private String endpoint;
    private String httpMethod;
    private Long successCount;
    private Long failureCount;
    private Double successRate;
    
}
