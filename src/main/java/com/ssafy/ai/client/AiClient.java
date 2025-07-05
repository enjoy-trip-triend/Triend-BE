package com.ssafy.ai.client;

public interface AiClient {

    String sendChatPrompt(String systemPrompt, String userPrompt);

    String sendJsonPrompt(String systemPrompt, String userPrompt);
}
