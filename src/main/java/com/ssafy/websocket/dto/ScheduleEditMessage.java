package com.ssafy.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEditMessage {
    private Long plannerId;
    private Long userId;
    private Long scheduleId;
    private String date;
    private String startTime;
    private Long placeId;
    private String content;
    private String placeUrl;
    private Integer idx;
    private String action;   // "ADD", "UPDATE", "DELETE"
    private String sessionId;
}
