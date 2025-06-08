package com.ssafy.common.security.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.member.dto.Member;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{

	private Member member;
	
	public Member getMember() {
		return member;
	}
	
	@Override
	public String getUsername() {
		return member.getEmail();
	}
	
	@Override
	public String getPassword() {
		return member.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
				new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
		);
	}
}
