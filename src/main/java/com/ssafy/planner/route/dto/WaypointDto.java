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
    
    public String name;
    public double lat;
    public double lng;
}

