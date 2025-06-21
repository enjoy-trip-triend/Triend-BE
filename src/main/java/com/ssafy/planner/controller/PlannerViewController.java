package com.ssafy.planner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/planners")
public class PlannerViewController {
    
    @GetMapping("/manage-page")
    public String planner() {
        return "planner/planner-manage";
    }
}
