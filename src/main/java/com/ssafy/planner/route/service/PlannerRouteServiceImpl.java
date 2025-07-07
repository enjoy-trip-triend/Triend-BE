package com.ssafy.planner.route.service;

import com.ssafy.planner.route.dto.RouteResponseDto;
import com.ssafy.planner.route.dto.WaypointDto;
import com.ssafy.planner.route.util.TspSolver;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PlannerRouteServiceImpl implements PlannerRouteService {
    
    public RouteResponseDto recommendRoute(List<WaypointDto> waypoints) {
        
        return TspSolver.solveTsp(waypoints);
    }
}

