package com.ssafy.plannershare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerShare {
    private Long id;
    private String secretCode;
    private String password;
    private Long plannerId;
}
