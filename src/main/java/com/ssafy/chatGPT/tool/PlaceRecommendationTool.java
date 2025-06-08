package com.ssafy.chatGPT.tool;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.ssafy.chatGPT.service.RecommendationService;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.myplace.dto.RecommendPlaceDto;
import static com.ssafy.chatGPT.constant.RecommendationConstants.*;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlaceRecommendationTool {
    private final RecommendationService recommendationService;
    private final MemberService memberService;

    @Tool(description = "같은 MBTI 사용자들이 저장한 장소 추천")
    public List<RecommendPlaceDto> getRecommendedPlacesByMBTI(String email) {
        Member me = memberService.getMemberByEmail(email);
        return recommendationService.recommendPlacesByMbti(me, DEFAULT_RECOMMEND_LIMIT);
    }
    
    @Tool(description = "비슷한 성향을 가진 사용자들이 저장한 장소 추천")
    public List<RecommendPlaceDto> getRecommendPlacesByCharacters(String email) {
    	Member me = memberService.getMemberByEmail(email);
    	return recommendationService.recommendByCharacters(me, DEFAULT_RECOMMEND_LIMIT);
    }
}
