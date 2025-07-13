package com.ssafy.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties props) {
        return new LettuceConnectionFactory(props.getHost(),
                props.getPort());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                // 모든 서브타입을 허용하는 일종의 “무검증” 검증기 개발 테스트 단계에서 사용
                LaissezFaireSubTypeValidator.instance,
                // 어떤 클래스들에 타입 정보를 붙일지를 결정하는 정책
                // NON_FINAL -> final 클래스가 아닌 모드 타입
                DefaultTyping.NON_FINAL,
                As.PROPERTY
        );

        Jackson2JsonRedisSerializer<Object> jsonSer = new Jackson2JsonRedisSerializer<>(
                objectMapper, Object.class);

        template.setValueSerializer(jsonSer);
        template.setHashValueSerializer(jsonSer);

        return template;
    }
}
