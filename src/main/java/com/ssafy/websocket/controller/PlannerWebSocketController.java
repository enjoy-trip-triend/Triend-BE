package com.ssafy.websocket.controller;

import com.ssafy.websocket.dto.JoinGroupMessage;
import com.ssafy.websocket.dto.ScheduleEditMessage;
import com.ssafy.websocket.service.PlannerWebSocketService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PlannerWebSocketController {

    private final PlannerWebSocketService plannerWebSocketService;

    /**
     * 사용자가 공동 편집방에 접속할 때 호출됨
     */
    @MessageMapping("/planner/join")
    public void joinPlanner(JoinGroupMessage joinMessage, Principal principal)
            throws UnknownHostException {
        String ipAddr = InetAddress.getLocalHost().getHostAddress();
        plannerWebSocketService.joinEditor(joinMessage, principal.getName(), ipAddr);
    }

    /**
     * 스케줄 수정 메시지를 수신했을 때 호출됨
     */
    @MessageMapping("/planner/schedule")
    public void editSchedule(ScheduleEditMessage message) {
        plannerWebSocketService.handleScheduleEdit(message);
    }
}
