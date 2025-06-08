package com.ssafy.chatGPT.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.chatGPT.dto.UserMessage;
import com.ssafy.chatGPT.service.ChatService;
import com.ssafy.chatGPT.service.RecommendationService;
import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import com.ssafy.myplace.dto.RecommendPlaceDto;
import static com.ssafy.chatGPT.constant.RecommendationConstants.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final RecommendationService recommendationService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody UserMessage userMessage, @AuthenticationPrincipal CustomUserDetails userDetails) {
    	Member member = userDetails.getMember();
        String reply = chatService.askChatGPT(userMessage, member);
        return ResponseEntity.ok(reply);
    }
    
    @GetMapping("/recommend/places/mbti")
    public ResponseEntity<List<RecommendPlaceDto>> getRecommendPlacesByMBTI(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	Member loginUser = userDetails.getMember();
    	List<RecommendPlaceDto> body = recommendationService.recommendPlacesByMbti(loginUser, DEFAULT_RECOMMEND_LIMIT);
    	return ResponseEntity.ok(body);
    }
    
    @GetMapping("/recommend/places/characters") 
    public ResponseEntity<List<RecommendPlaceDto>> getRecommendPlacesByCharacters(@AuthenticationPrincipal CustomUserDetails userDetails) {
    	Member loginUser = userDetails.getMember();
    	List<RecommendPlaceDto> body = recommendationService.recommendByCharacters(loginUser, DEFAULT_RECOMMEND_LIMIT);
    	return ResponseEntity.ok(body);
    }
}
