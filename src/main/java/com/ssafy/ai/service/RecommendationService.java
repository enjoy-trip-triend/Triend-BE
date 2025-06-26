package com.ssafy.ai.service;

import com.ssafy.ai.dto.RecommendationPlaceResponseDto;
import java.util.List;

import com.ssafy.member.dto.Member;

public interface RecommendationService {
    List<RecommendationPlaceResponseDto> recommendPlacesByMbti(Member me, int limit);
    List<RecommendationPlaceResponseDto> recommendByCharacters(Member me, int limit);
    List<RecommendationPlaceResponseDto> recommendCombined(Member me, int limit);
}
