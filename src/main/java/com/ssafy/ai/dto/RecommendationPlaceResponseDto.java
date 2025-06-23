package com.ssafy.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecommendationPlaceResponseDto {
    private String kakaoId;
    private String placeName;
    private String categoryName;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String addressName;
    private String roadAddressName;
    private double latitude;
    private double longitude;
    private String phone;
}
