package com.ssafy.websocket.listener;

import com.ssafy.websocket.manager.PlannerEditorManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final PlannerEditorManager editorManager;
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        Long plannerId = editorManager.getSessionPlannerMap().get(sessionId);

        editorManager.removeEditor(sessionId);

        if (plannerId != null) {
            messagingTemplate.convertAndSend("/topic/planner/" + plannerId + "/editors",
                    editorManager.getEditors(plannerId));

            log.info("WebSocket disconnected: sessionId={}, plannerId={}", sessionId, plannerId);
        }
    }
}
