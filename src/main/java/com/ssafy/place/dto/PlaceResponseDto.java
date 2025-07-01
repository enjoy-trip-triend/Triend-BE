package com.ssafy.place.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceResponseDto {
    private Long id;
    private Long kakaoId;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phone;
    private Long saveCount;
    private Long categoryId;
    private String categoryName;
}
