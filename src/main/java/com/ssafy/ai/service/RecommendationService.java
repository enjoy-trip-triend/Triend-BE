package com.ssafy.ai.service;

import java.util.List;

import com.ssafy.member.dto.Member;
import com.ssafy.myplace.dto.RecommendPlaceDto;

public interface RecommendationService {
    List<RecommendPlaceDto> recommendPlacesByMbti(Member me, int limit);
    List<RecommendPlaceDto> recommendByCharacters(Member me, int limit);
    List<RecommendPlaceDto> recommendCombined(Member me, int limit);
}
