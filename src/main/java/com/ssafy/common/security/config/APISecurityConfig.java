package com.ssafy.common.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.common.security.filter.JWTAuthenticationFilter;
import com.ssafy.common.security.filter.JWTVerificationFilter;
import com.ssafy.common.security.filter.SecurityExceptionHandlingFilter;
import com.ssafy.common.security.service.CustomUserDetailsService;

@Configuration
public class APISecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

	@Bean
	PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig,
            CustomUserDetailsService userDetailsService,
            JWTAuthenticationFilter authFilter,
            JWTVerificationFilter jwtVerifyFilter,
            SecurityExceptionHandlingFilter exceptionFilter)
            throws Exception {
    	
    	http.securityMatcher("/api/**")
    	.cors(t -> t.configurationSource(corsConfig))
    	.userDetailsService(userDetailsService)
    	.csrf(csrf -> csrf.disable())
    	.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    	
    	http.authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").permitAll()
    			.anyRequest().authenticated());
    	
    	http.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)
    	.addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
    	.addFilterBefore(exceptionFilter, JWTVerificationFilter.class);
        
    	// END
        return http.build();
    }

    // 06-2. Filter 수준에서 동작하기 위한 CorsConfigurationSource를 구성하고 적용하자.
    // WebMvcConfigurer는 cors 설정은 무의미
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));

        //“클라이언트(브라우저)가 쿠키, 인증 헤더(JWT 등), 세션 같은 자격 정보(credentials)를 서버로 보낼 수 있게 허용하겠다”
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/member/checkEmail", configuration);

        return source;
    }
    
}
