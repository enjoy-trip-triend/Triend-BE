package com.ssafy.websocket.manager;

import com.ssafy.websocket.dto.EditorInfo;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import org.springframework.stereotype.Component;
/*
 * 현재 WebSocket 으로 접속 중인 사용자(editor) 들을 서버에서 관리하는 클래스
 */
@Component
@Getter
public class PlannerEditorManager {
    private final ConcurrentHashMap<Long, List<EditorInfo>> plannerEditors = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> sessionPlannerMap = new ConcurrentHashMap<>();

    public void addEditor(EditorInfo editor) {
        plannerEditors.computeIfAbsent(editor.getPlannerId(), k -> new CopyOnWriteArrayList<>()).add(editor);
        sessionPlannerMap.put(editor.getSessionId(), editor.getPlannerId());
    }

    public void removeEditor(String sessionId) {
        Long plannerId = sessionPlannerMap.get(sessionId);
        if (plannerId == null) return;

        List<EditorInfo> editors = plannerEditors.get(plannerId);
        if (editors != null) {
            editors.removeIf(e -> e.getSessionId().equals(sessionId));
            if (editors.isEmpty()) {
                plannerEditors.remove(plannerId);
            }
        }
        sessionPlannerMap.remove(sessionId);
    }

    public List<EditorInfo> getEditors(Long plannerId) {
        return plannerEditors.getOrDefault(plannerId, List.of());
    }
}

/*
ConcurrentHashMap
→ 여러 사용자가 동시에 접속/끊김 하므로 thread-safe 하게 관리 필요!

CopyOnWriteArrayList
→ editorList 도 동시에 읽기/쓰기 발생 가능
→ thread-safe 한 list 필요

 */
