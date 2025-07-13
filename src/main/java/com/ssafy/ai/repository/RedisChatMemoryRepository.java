package com.ssafy.ai.repository;

import io.micrometer.common.lang.NonNullApi;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
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
                .map(m -> (Message) m)
                .toList();
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        String key = KEY_PREDIX + conversationId;
        deleteByConversationId(key);

        BoundListOperations<String, Object> ops = redisTemplate.boundListOps(key);
        for (Message m : messages) {
            ops.rightPush(m);
        }
        redisTemplate.expire(key, TTL);
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        redisTemplate.delete(KEY_PREDIX + conversationId);
    }
}
