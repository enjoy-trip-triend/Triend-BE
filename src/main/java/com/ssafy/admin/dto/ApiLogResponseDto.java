package com.ssafy.admin.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogResponseDto {
    
    private Long id;
    private String endpoint;
    private String httpMethod;
    private Integer statusCode;
    private Long responseTimeMs;
    private String ipAddress;
    private String userAgent;
    private String requestBody;
    private LocalDateTime createdAt;
    private ApiLogMemberResponseDto member;
    
}
