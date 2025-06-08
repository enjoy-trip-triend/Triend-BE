package com.ssafy.member.service;

import java.util.List;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;

public interface MemberService {
	void createMember(Member member);
	Member getMemberByEmail(String email);
	boolean isEmailAvailable(String email);
	List<CharacterDTO> getCharacters();
	List<CharacterDTO> getCharacterByMemberId(Long memberId);
	void updateMember(Member member);
}
