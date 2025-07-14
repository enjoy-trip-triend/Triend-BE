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
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
        deleteByConversationId(key);

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

        BoundListOperations<String, Object> ops = redisTemplate.boundListOps(key);
        for (MessageInfoDTO messageInfoDTO : meesageInfoList) {
            ops.rightPush(messageInfoDTO);
        }
        redisTemplate.expire(key, TTL);
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        redisTemplate.delete(KEY_PREDIX + conversationId);
    }
}
