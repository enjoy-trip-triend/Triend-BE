package com.ssafy.websocket.service;

import com.ssafy.websocket.dto.JoinGroupMessage;
import com.ssafy.websocket.dto.ScheduleEditMessage;

public interface PlannerWebSocketService {

    void joinEditor(JoinGroupMessage joinMessage, String sessionId, String ipAddr);

    void handleScheduleEdit(ScheduleEditMessage message);
}
