package com.ssafy.member.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {
	private Long id;
	private String email;
	private String password;
	private String name;
	private Role role;
	private String mbti;
	private LocalDate birth; 
	private List<Long> characters;
	private String refreshToken;
}
