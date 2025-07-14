package com.ssafy.planner.route.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDto {
    
    private List<Long> route;
    private double totalDistance;
}
