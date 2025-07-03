package com.ssafy.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ai.client.AiClient;
import com.ssafy.ai.dto.UserMessage;
import com.ssafy.ai.prompt.PromptBuilder;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.member.dto.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiServiceImpl implements AiService {

    private final AiClient aiClient;
    private final PromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;

    @Override
    public String chatWithAi(Member member, UserMessage message) {
        return null;
    }

    /**
     * 카카오 맵 API로 받은 장소를 AI가 사용자 맞춤으로 필터를 합니다.
     *
     * @param member 현재 로그인한 멤버
     * @param places 카카오 맵 API로 받은 관광지 후보
     * @return AI가 사용자 맞춤으로 추천하는 장소 반환
     */
    @Override
    public List<Document> filterAttractionsByAi(Member member,
            List<KakaoSearchResponseDto.Document> places) {
        String userPrompt = promptBuilder.buildRecommendationPrompt(places);
        String result = aiClient.sendPrompt(member, userPrompt);
        try {
            return objectMapper.readValue(result.trim(), new TypeReference<List<Document>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("AI 응답 파싱 실패, 원본 응답: {}", result, e);
            throw new RuntimeException("AI 필터링 결과를 파싱하는 중 오류가 발생했습니다.", e);
        }
    }
}
