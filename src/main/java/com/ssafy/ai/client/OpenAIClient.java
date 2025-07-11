package com.ssafy.ai.client;

import com.ssafy.ai.tool.MemberTool;
import com.ssafy.ai.tool.PlaceRecommendationTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAIClient implements AIClient {

    private final ChatClient chatClient;
    private final MemberTool memberTool;
    private final PlaceRecommendationTool placeRecommendationTool;


    @Override
    public String sendChatPrompt(String systemPrompt, String userPrompt) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .tools(memberTool, placeRecommendationTool)
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