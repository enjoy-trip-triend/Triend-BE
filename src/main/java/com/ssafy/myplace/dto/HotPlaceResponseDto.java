package com.ssafy.myplace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotPlaceResponseDto {
	private String kakaoId;
	private String address;
	private Long count;
}
