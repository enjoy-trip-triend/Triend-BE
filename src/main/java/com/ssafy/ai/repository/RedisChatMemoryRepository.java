package com.ssafy.ai.repository;

import com.ssafy.ai.dto.MessageInfoDTO;
import com.ssafy.ai.dto.AIRole;
import io.micrometer.common.lang.NonNullApi;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

@Repository
@NonNullApi
@RequiredArgsConstructor
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    private static final String KEY_PREDIX = "chat:member:";
    private static final Duration TTL = Duration.ofDays(30);
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<String> findConversationIds() {
        Set<String> keys = redisTemplate.keys(KEY_PREDIX + "*");

        if (keys.isEmpty()) {
            return Collections.emptyList();
        }

        return keys.stream()
                .map(key -> key.substring(KEY_PREDIX.length()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {
        String key = KEY_PREDIX + conversationId;
        List<Object> range = redisTemplate.opsForList()
                .range(key, 0, -1);

        return range.stream()
                .map(o -> (MessageInfoDTO) o)
                .map(dto -> {
                    // 반환 타입을 Message
                    Message msg;
                    if (dto.getRole() == AIRole.USER) {
                        msg = new UserMessage(dto.getContent());
                    } else {
                        msg = new AssistantMessage(dto.getContent());
                    }
                    return msg;
                })
                .toList();
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        String key = KEY_PREDIX + conversationId;

        List<MessageInfoDTO> meesageInfoList = messages.stream()
                .map(m -> {
                    AIRole role = AIRole.valueOf(m.getMessageType()
                            .name());
                    String content = m.getText();
                    Long timestamp = Instant.now()
                            .toEpochMilli();

                    return new MessageInfoDTO(role, content, timestamp);
                })
                .toList();

        // SessionCallback으로 MULTI/EXEC 블록 묶기
        redisTemplate.execute(new SessionCallback<Void>() { // connection을 유지
            @SuppressWarnings("unchecked")
            @Override
            public Void execute(RedisOperations ops) throws DataAccessException {
                ops.multi();                                     // MULTI 시작
                ops.delete(key);                                 // 1) 기존 삭제

                BoundListOperations<String, Object> bound = ops.boundListOps(key);
                for (MessageInfoDTO dto : meesageInfoList) {
                    bound.rightPush(dto);                        // 2) 삽입
                }

                ops.expire(key, TTL);                            // 3) TTL 설정
                ops.exec();                                      // EXEC 커밋
                return null;
            }
        });
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        redisTemplate.delete(KEY_PREDIX + conversationId);
    }
}
