package com.ssafy.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.member.dto.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {

	private final SecretKey key;

	public JWTUtil() {
		key = Jwts.SIG.HS256.key().build();
	}

	@Value("${triend.jwt.access-expmin}")
	private Long accessExpMin;
	@Value("${triend.jwt.refresh-expmin}")
	private Long refreshExpMin;

	public String createAccessToken(Member member) {
		return create("accessToken", accessExpMin,
				Map.of("email", member.getEmail(), "name", member.getName(), "role", member.getRole()));
	}

	public String createRefreshToken(Member member) {
		return create("refreshToken", refreshExpMin, Map.of("email", member.getEmail()));
	}

	public String create(String subject, long expireMin, Map<String, Object> claims) {

		Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * expireMin);

		String token = Jwts.builder().subject(subject).claims(claims).expiration(expireDate).signWith(key).compact();
		log.debug("token 생성 : {}", token);
		
		return token;
	}

	public Claims getClaims(String jwt) {

		var parser = Jwts.parser().verifyWith(key).build();
		var jwts = parser.parseSignedClaims(jwt);
		log.debug("claim: {}", jwts.getPayload());
		
		return jwts.getPayload();

	}
}
