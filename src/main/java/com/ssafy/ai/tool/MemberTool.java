package com.ssafy.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberTool {
	
	private final MemberService memberService;
	
	@Tool(description = "회원 email을 이용해 회원 정보를 조회합니다.")
	public Member getMemberInfo(String email) {
		return memberService.getMemberByEmail(email);
	}
}
