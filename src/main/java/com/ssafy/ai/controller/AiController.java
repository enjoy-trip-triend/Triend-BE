package com.ssafy.ai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.ai.dto.UserMessage;
import com.ssafy.ai.service.ChatService;
import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody UserMessage userMessage,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        String reply = chatService.askChatGPT(userMessage, member);
        return ResponseEntity.ok(reply);
    }
}
