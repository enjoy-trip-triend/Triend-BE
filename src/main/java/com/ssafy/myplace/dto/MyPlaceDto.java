package com.ssafy.myplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyPlaceDto {
	private Long id;
	private String kakaoId;
	private String address;
	private double lat;
	private double lon;
	private String locationName;
	private String description;
	private Long categoryId;
	private String categoryName;
}
