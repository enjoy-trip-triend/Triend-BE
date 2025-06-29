package com.ssafy.websocket.handler;

import com.ssafy.websocket.dto.StompPrincipal;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("[DEBUG] Handshake Principal 생성: " + uuid);
        return new StompPrincipal(uuid);
    }

}
