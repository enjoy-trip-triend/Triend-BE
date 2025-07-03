package com.ssafy.ai.controller;

import com.ssafy.ai.service.AttractionRecommendationService;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionRecommendationService attractionRecommendationService;

    @GetMapping("/recommendations")
    public ResponseEntity<List<Document>> getAttractionsByAi(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam List<String> regions) {

        Member loginMember = customUserDetails.getMember();
        List<Document> documents = attractionRecommendationService.recommendAttraction(loginMember,
                regions);
        return ResponseEntity.ok(documents);
    }
}
