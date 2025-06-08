package com.ssafy.member.service;

import java.util.List;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void createMember(Member member) {
		
		String encodedPwdString = passwordEncoder.encode(member.getPassword());
		member.setPassword(encodedPwdString);
		
		memberMapper.insertMember(member);
		if(Objects.nonNull(member.getCharacters()) && !member.getCharacters().isEmpty()) {
			Member m = memberMapper.selectMemberByEmail(member.getEmail());
			memberMapper.insertMembersCharacters(m.getId(), member.getCharacters());
		}
		
	}

	@Override
	public boolean isEmailAvailable(String email) {
		return memberMapper.selectMemberByEmail(email)==null;
	}

	@Override
	public List<CharacterDTO> getCharacters() {
		return memberMapper.selectCharacters();
	}

	@Override
	public Member getMemberByEmail(String email) {
		return memberMapper.selectMemberByEmail(email);
	}

	@Override
	public List<CharacterDTO> getCharacterByMemberId(Long memberId) {
		log.debug("member service 멤버의 캐릭터 조회: {}", memberMapper.selectCharactersById(memberId));
		return memberMapper.selectCharactersById(memberId);
	}
	
	@Override
	public void updateMember(Member member) {
		int cnt  = memberMapper.updateMember(member);

		if(cnt != 1) {
			throw new RuntimeException("[ERROR] 리프레쉬 토큰 업데이트 실패");
		}
	}
}
