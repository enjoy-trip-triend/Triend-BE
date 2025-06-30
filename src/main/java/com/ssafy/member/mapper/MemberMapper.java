package com.ssafy.member.mapper;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.dto.MemberResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    
    List<MemberResponseDto> selectAllMembers();
    
    int insertMember(Member member);
    
    int insertMembersCharacters(Long memberId, List<Long> characters);
    
    Member selectMemberByEmail(String email);
    
    List<CharacterDTO> selectCharacters();
    
    List<CharacterDTO> selectCharactersById(Long memberId);
    
    int updateMember(Member member);
}
