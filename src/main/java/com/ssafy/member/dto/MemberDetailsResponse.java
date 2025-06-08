package com.ssafy.member.dto;

import java.time.LocalDate;
import java.util.List;

public record MemberDetailsResponse(String email, String name, Role role, String mbti, LocalDate birth,
		List<String> characters) {

}
