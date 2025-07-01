package com.ssafy.member.service;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.dto.MemberResponseDto;
import java.util.List;

public interface MemberService {
    
    List<MemberResponseDto> getAllMembers();
    
    void createMember(Member member);
    
    Member getMemberByEmail(String email);
    
    boolean isEmailAvailable(String email);
    
    List<CharacterDTO> getCharacters();
    
    List<CharacterDTO> getCharacterByMemberId(Long memberId);
    
    void updateMember(Member member);
}
