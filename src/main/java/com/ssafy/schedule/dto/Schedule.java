package com.ssafy.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    
    private Long id;
    private Long plannerId;
    private LocalDate date;
    private LocalTime startTime;
    private Long placeId;
    private String content;
    private String placeUrl;
    private Integer idx;
}
