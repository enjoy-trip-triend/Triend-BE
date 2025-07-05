package com.ssafy.ai.service;

import static com.ssafy.client.kakao.constant.KakaoApiConstants.*;

import com.ssafy.client.kakao.CategoryGroupCode;
import com.ssafy.client.kakao.KakaoMapService;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.member.dto.Member;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionRecommendationService {

    private final KakaoMapService kakaoMapService;
    private final AIService aiService;

    /**
     * 사용자에게 맞춤형 관광지를 추천해줍니다.
     *
     * @param member  현재 로그인한 멤버
     * @param regions 사용자가 플래너에 담은 지역
     * @return 사용자 맞춤 관광지 장소 반환
     */
    public List<Document> recommendAttraction(Member member,
            List<String> regions) {

        if (regions == null || regions.isEmpty()) {
            return Collections.emptyList();
        }

        List<Document> documents = regions.stream()
                .flatMap(region -> kakaoMapService.searchPlacesByKeyword(region,
                                CategoryGroupCode.AT4, DEFAULT_PAGE, DEFAULT_SIZE)
                        .stream())
                .distinct()
                .toList();

        log.debug("documents: {}", documents);

        if (documents.isEmpty()) {
            return Collections.emptyList();
        }

        return aiService.filterAttractionsByAi(member, documents);
    }

}
