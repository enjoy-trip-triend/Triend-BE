package com.ssafy.planner.schedule.dto;

import com.ssafy.place.dto.PlaceResponseDto;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    
    private Long id;
    private Long plannerId;
    @Setter
    private LocalDate date;
    private LocalTime startTime;
    private String content;
    private Integer idx;
    private PlaceResponseDto place;
}
