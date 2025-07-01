package com.ssafy.member.controller;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.dto.MemberDetailsResponse;
import com.ssafy.member.dto.MemberResponseDto;
import com.ssafy.member.dto.Role;
import com.ssafy.member.service.MemberService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    
    private final MemberService memberService;
    
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        if (member.getRole() != Role.ADMIN) {
            log.warn("Unauthorized access attempt by user: {}", member.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .build();
        }
        return ResponseEntity.ok(memberService.getAllMembers());
    }
    
    @GetMapping("/details")
    public ResponseEntity<MemberDetailsResponse> getMemberDetails(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.debug("getMemberDetails Method");
        Member member = userDetails.getMember();
        List<CharacterDTO> memberCharactersDto = memberService.getCharacterByMemberId(member.getId());
        log.debug("사용자정보 성향 조회: {}", memberCharactersDto);
        List<String> memberCharacters = memberCharactersDto.stream()
                .map(CharacterDTO::getName)
                .toList();
        MemberDetailsResponse body = new MemberDetailsResponse(
                member.getEmail(),
                member.getName(),
                member.getRole(),
                member.getMbti(),
                member.getBirth(),
                memberCharacters);
        
        return ResponseEntity.ok(body);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody Member member) {
        log.debug("member: {}", member);
        memberService.createMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean available = memberService.isEmailAvailable(email);
        log.info("email-duplicate-check: {}", available);
        return Map.of("available", available);
    }
    
    @GetMapping("/characters")
    public ResponseEntity<List<CharacterDTO>> getCharacters() {
        List<CharacterDTO> characters = memberService.getCharacters();
        return ResponseEntity.ok(characters);
    }
}
