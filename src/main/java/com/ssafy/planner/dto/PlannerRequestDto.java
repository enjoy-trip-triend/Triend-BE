package com.ssafy.planner.dto;

import java.time.LocalDate;
import java.util.List;


public record PlannerRequestDto(
    LocalDate startDay,
    LocalDate endDay,
    String name,
    String comment, // Nullable
    Exposure exposure,
    List<PlannerLocationDto> locations // Nullable
) {

}
