package com.ssafy.ai.client;

import com.ssafy.member.dto.Member;

public interface AiClient {

    String sendPrompt(Member member, String message);
}
