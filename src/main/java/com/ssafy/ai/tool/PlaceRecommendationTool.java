package com.ssafy.ai.tool;

import com.ssafy.place.dto.PlaceResponseDto;
import com.ssafy.place.service.PlaceService;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.ssafy.ai.service.RecommendationService;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.ai.dto.RecommendationPlaceResponseDto;
import static com.ssafy.ai.constant.RecommendationConstants.*;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlaceRecommendationTool {
    private final PlaceService placeService;
    private final MemberService memberService;

    @Tool(description = "같은 MBTI 사용자들이 저장한 장소 추천")
    public List<PlaceResponseDto> getRecommendedPlacesByMBTI(String email) {
        Member me = memberService.getMemberByEmail(email);
        return placeService.getTopPlacesByMbti(me);
    }

    @Tool(description = "비슷한 성향을 가진 사용자들이 저장한 장소 추천")
    public List<PlaceResponseDto> getRecommendPlacesByCharacters(String email) {
    	Member me = memberService.getMemberByEmail(email);
    	return placeService.getTopPlacesByCharacter(me);
    }
}
