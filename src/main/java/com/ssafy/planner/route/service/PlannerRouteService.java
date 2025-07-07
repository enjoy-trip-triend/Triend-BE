package com.ssafy.planner.route.service;

import com.ssafy.planner.route.dto.RouteResponseDto;
import com.ssafy.planner.route.dto.WaypointDto;
import java.util.List;

public interface PlannerRouteService {
    
    RouteResponseDto recommendRoute(List<WaypointDto> waypoints);
}
