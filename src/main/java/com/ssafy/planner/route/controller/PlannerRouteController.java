package com.ssafy.planner.route.controller;

import com.ssafy.planner.route.dto.RouteResponseDto;
import com.ssafy.planner.route.dto.WaypointDto;
import com.ssafy.planner.route.service.PlannerRouteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planner/routes")
@RequiredArgsConstructor
public class PlannerRouteController {
    
    private final PlannerRouteService routeService;
    
    @PostMapping("/recommend")
    public RouteResponseDto recommend(@RequestBody List<WaypointDto> waypoints) {
        return routeService.recommendRoute(waypoints);
    }
}

