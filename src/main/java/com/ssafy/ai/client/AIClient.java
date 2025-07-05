package com.ssafy.ai.client;

public interface AIClient {

    String sendChatPrompt(String systemPrompt, String userPrompt);

    String sendJsonPrompt(String systemPrompt, String userPrompt);
}
