package com.ssafy.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
	private Long id;
	private String email;
	private String name;
	private String role;
}
