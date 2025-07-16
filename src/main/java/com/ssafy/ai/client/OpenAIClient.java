package com.ssafy.ai.client;

import com.ssafy.ai.tool.MemberTool;
import com.ssafy.ai.tool.PlaceRecommendationTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;

@Component
@RequiredArgsConstructor
public class OpenAIClient implements AIClient {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final MemberTool memberTool;
    private final PlaceRecommendationTool placeRecommendationTool;


    @Override
    public String sendChatPrompt(Long memberId, String systemPrompt, String userPrompt) {
        String conversationId = memberId.toString();

        MessageChatMemoryAdvisor build = MessageChatMemoryAdvisor.builder(chatMemory)
                .conversationId(conversationId)
                .order(1)
                .build();

        return chatClient.prompt()
                .system(systemPrompt)
                .tools(memberTool, placeRecommendationTool)
                .advisors(build)
                .user(userPrompt)
                .call()
                .content();
    }

    @Override
    public String sendJsonPrompt(String systemPrompt, String userPrompt) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();
    }
}