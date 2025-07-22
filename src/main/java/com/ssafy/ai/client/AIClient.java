package com.ssafy.ai.client;

public interface AIClient {

    String sendChatPrompt(Long memberId, String systemPrompt, String userPrompt);

    String sendJsonPrompt(String systemPrompt, String userPrompt);
}
