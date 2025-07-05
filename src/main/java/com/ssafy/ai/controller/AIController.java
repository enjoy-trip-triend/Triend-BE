package com.ssafy.ai.controller;

import com.ssafy.ai.service.AiService;
import com.ssafy.ai.service.AttractionRecommendationService;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.ai.dto.UserMessage;
import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AiService aiService;
    private final AttractionRecommendationService attractionRecommendationService;

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithAI(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody UserMessage userMessage) {
        Member member = customUserDetails.getMember();
        String reply = aiService.chatWithAi(member, userMessage);
        return ResponseEntity.ok(reply);
    }

    @GetMapping("/recommendation/places")
    public ResponseEntity<List<Document>> getRecommendedAttractions(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam List<String> regions) {

        Member loginMember = customUserDetails.getMember();
        List<Document> documents = attractionRecommendationService.recommendAttraction(loginMember,
                regions);
        return ResponseEntity.ok(documents);
    }
}
