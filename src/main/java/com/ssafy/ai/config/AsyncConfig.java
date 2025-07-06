package com.ssafy.ai.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean
    public Executor kakaoTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);    // 풀에서 항상 유지할 최소한의 쓰레드 수
        executor.setMaxPoolSize(10);    // 쓰레드풀의 작업 대기 큐 크기
        executor.setQueueCapacity(50);  // 쓰레드풀에서 최대 생성할 수 있는 쓰레드의 상한선
        executor.setThreadNamePrefix("kakao-task-thread-");  // 생성된 쓰레드의 접두사를 설정
        executor.initialize();
        return executor;
    }
}
