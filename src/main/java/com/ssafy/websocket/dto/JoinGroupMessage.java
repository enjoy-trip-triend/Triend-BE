package com.ssafy.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * planner 수정 그룹에 connection 요청 객체
 */
public class JoinGroupMessage {
    private Long plannerId;
    private Long userId;
}
