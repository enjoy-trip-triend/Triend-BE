package com.ssafy.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker   // Websocket 메시지 핸들링이 가능하게 하는 broker를 사용한다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * broker 설정하는 디폴트 메서드.
     * @param config MessageBrokerRegistry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        // 클라이언트 목적지를 topic으로 지정
        config.enableSimpleBroker("/topic");

        // 메시지 목적지를 MessageMapping된 메서드에 바인딩
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * /gs-guide-websocket endpoint를 등록하는 메서드로,
     * WebSocket을 못쓸때 SockJS를 fallback option으로 쓸 수 있게 한다.
     * @param registry StompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/triend-websocket").setAllowedOrigins("*").withSockJS();
    }

}
