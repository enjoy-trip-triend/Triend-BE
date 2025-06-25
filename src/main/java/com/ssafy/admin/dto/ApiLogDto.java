package com.ssafy.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiLogDto {
    
    private Long id;
    private Long memberId;
    private String endpoint;
    private String httpMethod;
    private Integer statusCode;
    private Long responseTimeMs;
    private String ipAddress;
    private String userAgent;
    private String requestBody;
}
