package com.ssafy.myplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecommendPlaceDto {
    private Long id;
    private String kakaoId;
    private String address;
    private double lat;
    private double lon;
    private String locationName;
}
