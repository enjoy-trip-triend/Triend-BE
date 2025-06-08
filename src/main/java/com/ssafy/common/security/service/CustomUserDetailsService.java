package com.ssafy.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import com.ssafy.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("user-email: {}", username);
		Member member = memberMapper.selectMemberByEmail(username);
		log.debug("member: {}", member);

		if(member == null) {
			throw new UsernameNotFoundException("[ERROR] 해당 이메일의 사용자를 찾지 못했습니다. " + username);
		}
		
	    return new CustomUserDetails(member);
	}

}
