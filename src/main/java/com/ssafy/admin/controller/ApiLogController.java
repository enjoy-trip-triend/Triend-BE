package com.ssafy.admin.controller;

import com.ssafy.admin.dto.ApiLogCountResponseDto;
import com.ssafy.admin.dto.ApiLogLatencyResponseDto;
import com.ssafy.admin.dto.ApiLogResponseDto;
import com.ssafy.admin.dto.ApiLogStatusRateResponseDto;
import com.ssafy.admin.service.ApiLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class ApiLogController {
    
    private final ApiLogService apiLogService;
    
    @GetMapping
    public ResponseEntity<List<ApiLogResponseDto>> getApiLogs(
            @RequestParam(name = "from", required = false) LocalDateTime from,
            @RequestParam(name = "to", required = false) LocalDateTime to,
            @RequestParam(value = "member-id", required = false) Long memberId
    ) {
        return ResponseEntity.ok(apiLogService.getApiLogs(from, to, memberId));
    }
    
    @GetMapping("/stats/counts")
    public ResponseEntity<List<ApiLogCountResponseDto>> getApiLogCount(
            @RequestParam(name = "from", required = false) LocalDateTime from,
            @RequestParam(name = "to", required = false) LocalDateTime to,
            @RequestParam(name = "member-id", required = false) Long memberId
    ) {
        return ResponseEntity.ok(apiLogService.getApiLogCount(from, to, memberId));
    }
    
    @GetMapping("/stats/status-rate")
    public ResponseEntity<List<ApiLogStatusRateResponseDto>> getApiLogStatusRate(
            @RequestParam(name = "from", required = false) LocalDateTime from,
            @RequestParam(name = "to", required = false) LocalDateTime to
    ) {
        return ResponseEntity.ok(apiLogService.getApiLogStatus(from, to));
    }
    
    @GetMapping("/stats/latency")
    public ResponseEntity<List<ApiLogLatencyResponseDto>> getApiLogLatency(
            @RequestParam(name = "from", required = false) LocalDateTime from,
            @RequestParam(name = "to", required = false) LocalDateTime to
    ) {
        return ResponseEntity.ok(apiLogService.getApiLogLatency(from, to));
    }
}
