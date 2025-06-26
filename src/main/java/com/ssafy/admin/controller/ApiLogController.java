package com.ssafy.admin.controller;

import com.ssafy.admin.dto.ApiLogDto;
import com.ssafy.admin.service.ApiLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class ApiLogController {
    
    private final ApiLogService apiLogService;
    
    @GetMapping
    public ResponseEntity<List<ApiLogDto>> getApiLogs(
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime
    ) {
        return ResponseEntity.ok(apiLogService.getApiLogsByDateRange(startDateTime, endDateTime));
    }
    
    @GetMapping("/members/{member-id}")
    public ResponseEntity<List<ApiLogDto>> getApiLogsByMember(
            @PathVariable("member-id") Long memberId) {
        return ResponseEntity.ok(apiLogService.getApiLogsByMember(memberId));
    }
}
