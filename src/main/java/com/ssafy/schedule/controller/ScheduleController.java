package com.ssafy.schedule.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.schedule.dto.ScheduleCreateRequestDto;
import com.ssafy.schedule.dto.SchedulesOrderUpdateRequestDto;
import com.ssafy.schedule.service.ScheduleService;
import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    
    private final ScheduleService scheduleService;
    
    @PostMapping
    public ResponseEntity<Long> createSchedules(@AuthenticationPrincipal CustomUserDetails loginUser,
            @RequestBody ScheduleCreateRequestDto request) {
        scheduleService.createSchedules(request, loginUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()   // /api/schedules
                .build()
                .toUri();
        
        return ResponseEntity.created(location)
                .build();
    }
    
    @GetMapping("/images")
    public ResponseEntity<Map<Long, List<String>>> getPresignedImageUrlsByScheduleIds(
            @RequestParam List<Long> scheduleIds) {
        return ResponseEntity.ok(scheduleService.getPresignedImageUrlsByScheduleIds(scheduleIds));
    }
    
    @PutMapping("/schedules/order")
    public ResponseEntity<Void> updateSchedulesOrder(
            @AuthenticationPrincipal CustomUserDetails loginUser,
            @RequestBody SchedulesOrderUpdateRequestDto request) {
        
        scheduleService.updateSchedulesOrder(request, loginUser);
        
        return ResponseEntity.noContent()
                .build();
    }
}
