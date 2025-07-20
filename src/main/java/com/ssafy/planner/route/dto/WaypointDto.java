package com.ssafy.planner.route.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WaypointDto {
    
    private Long scheduleId;
    private double lat;
    private double lng;
}

