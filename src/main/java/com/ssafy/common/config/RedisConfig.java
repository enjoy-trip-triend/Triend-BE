package com.ssafy.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule()) // 생성자 기반 DTO
                .registerModule(new Jdk8Module())           // Optional 타입 필드
                .registerModule(new JavaTimeModule());      // java.time

        // 검증 없이 모든 하위 타입을 허용함
        // 개발 테스트 단계에서 사용
        PolymorphicTypeValidator ptv = LaissezFaireSubTypeValidator.instance;
        objectMapper.activateDefaultTyping(ptv, DefaultTyping.NON_FINAL, As.PROPERTY);

        // 직렬화
        Jackson2JsonRedisSerializer<Object> jsonSer = new Jackson2JsonRedisSerializer<>(
                objectMapper, Object.class);

        template.setValueSerializer(jsonSer);
        template.setHashValueSerializer(jsonSer);

        return template;
    }
}
