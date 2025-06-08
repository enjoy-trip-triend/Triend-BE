package com.ssafy.common.security.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.LoginUserResponse;
import com.ssafy.member.dto.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String rememberMe = request.getParameter("remember-me");
		log.debug("remember-me: {}", rememberMe);
		Cookie cookie;
		if (Objects.isNull(rememberMe)) {
			cookie = new Cookie("remember-me", null);
			cookie.setMaxAge(0);
		} else {
			cookie = new Cookie("remember-me", authentication.getName());
			cookie.setMaxAge(60*60*30);
		}
		response.addCookie(cookie);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
    	CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    	Member member = principal.getMember();
    	LoginUserResponse body = new LoginUserResponse(member.getEmail(), member.getRole().toString());
        objectMapper.writeValue(response.getWriter(), body);
	}
}
