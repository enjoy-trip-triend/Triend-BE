package com.ssafy.schedule.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.schedule.dto.ScheduleCreateRequest;
import com.ssafy.schedule.service.ScheduleService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    
    @PostMapping
    public ResponseEntity<Long> createSchedule(@AuthenticationPrincipal CustomUserDetails loginUser,
            @RequestBody ScheduleCreateRequest request) {
        Long scheduleId = scheduleService.createSchedule(request, loginUser);
        URI location = URI.create("/api/schedules/" + scheduleId);
        return ResponseEntity.created(location)
                .body(scheduleId);
    }
    
    @GetMapping("/images")
    public ResponseEntity<Map<Long, List<String>>> getPresignedImageUrlsByScheduleIds(
            @RequestParam List<Long> scheduleIds) {
        return ResponseEntity.ok(scheduleService.getPresignedImageUrlsByScheduleIds(scheduleIds));
    }
}
