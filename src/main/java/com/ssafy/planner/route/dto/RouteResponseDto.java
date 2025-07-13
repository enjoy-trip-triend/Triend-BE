package com.ssafy.planner.route.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDto {
    
    public List<Long> route;
    public double totalDistance;
}
