package com.ssafy.websocket.service;

import com.ssafy.schedule.dto.Schedule;
import com.ssafy.schedule.mapper.ScheduleMapper;
import com.ssafy.websocket.dto.EditorInfo;
import com.ssafy.websocket.dto.JoinGroupMessage;
import com.ssafy.websocket.dto.ScheduleEditMessage;
import com.ssafy.websocket.manager.PlannerEditorManager;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlannerWebSocketServiceImpl implements PlannerWebSocketService {
    private final PlannerEditorManager editorManager;
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional
    public void joinEditor(JoinGroupMessage joinMessage, String sessionId, String ipAddr) {
        EditorInfo editor = EditorInfo.builder()
                .sessionId(sessionId)
                .ipAddr(ipAddr)
                .plannerId(joinMessage.getPlannerId())
                .build();

        editor.setName();
        editorManager.addEditor(editor);

        // 브로드캐스트: 현재 참여자 목록을 모두에게 보냄
        messagingTemplate.convertAndSend("/topic/planner/" + joinMessage.getPlannerId() + "/editors",
                editorManager.getEditors(joinMessage.getPlannerId()));

        log.info("JOIN: plannerId={}, sessionId={}, name={}", joinMessage.getPlannerId(), sessionId, editor.getName());
    }

    @Override
    @Transactional
    public void handleScheduleEdit(ScheduleEditMessage message) {
        log.info("SCHEDULE_EDIT: {}", message);

        Schedule schedule = null;
        // 메시지에서 스케줄 객체로 변환
        if (!"DELETE".equals(message.getAction())) {
            schedule = Schedule.builder()
                    .id(message.getScheduleId())
                    .plannerId(message.getPlannerId())
                    .date(LocalDate.parse(message.getDate()))
                    .startTime(LocalTime.parse(message.getStartTime()))
                    .placeId(message.getPlaceId())
                    .content(message.getContent())
                    .placeUrl(message.getPlaceUrl())
                    .idx(message.getIdx())
                    .build();
        }

        // DB 처리
        switch (message.getAction()) {
            case "ADD" -> scheduleMapper.createSchedule(schedule);
            case "UPDATE" -> scheduleMapper.updateSchedule(schedule);
            case "DELETE" -> scheduleMapper.deleteSchedule(message.getScheduleId());
        }

        // 다른 사용자에게 브로드캐스트
        messagingTemplate.convertAndSend("/topic/planner/" + message.getPlannerId() + "/schedule", message);
    }
}
