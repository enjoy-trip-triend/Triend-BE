package com.ssafy.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;

@Mapper
public interface MemberMapper {
	int insertMember(Member member);
	int insertMembersCharacters(Long memberId, List<Long> characters);
	Member selectMemberByEmail(String email);
	List<CharacterDTO> selectCharacters();
	List<CharacterDTO> selectCharactersById(Long memberId);
	int updateMember(Member member);
}
