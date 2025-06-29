package com.ssafy.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogLatencyResponseDto {
    
    private String endpoint;
    private String httpMethod;
    private Double averageLatencyMs;
    
}
