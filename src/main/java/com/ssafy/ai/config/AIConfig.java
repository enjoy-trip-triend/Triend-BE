package com.ssafy.ai.config;

import com.ssafy.ai.repository.RedisChatMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI 관련 설정을 구성하는 클래스입니다.
 *
 * Spring AI 스타터가 자동으로 제공하는 ChatClient.Builder를
 * 이용해 ChatClient 빈을 생성합니다.
 */
@Configuration
@RequiredArgsConstructor
public class AIConfig {

    private final RedisChatMemoryRepository redisChatMemoryRepository;

    /**
     * ChatClient를 빈으로 등록합니다.
     *
     * @param chatClientBuilder Spring AI가 자동 구성한 Builder
     * @return 구체적인 ChatClient 인스턴스
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        // Builder에 설정된 API 키, 모델, 토큰 제한, 온도 등이 적용된 상태로 ChatClient를 생성
        return chatClientBuilder.build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(redisChatMemoryRepository)
                .maxMessages(100)
                .build();
    }
}