package com.ssafy.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.ai.client.AiClient;
import com.ssafy.ai.dto.UserMessage;
import com.ssafy.ai.builder.PromptBuilder;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import java.util.List;
import java.util.Objects;
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
    private final MemberService memberService;

    /**
     * 챗봇과 대화를 할 수 있습니다.
     *
     * @param member      현재 로그인한 멤버
     * @param userMessage 챗봇에게 보낼 사용자 메세지
     * @return 사용자 메세지에 대한 응답
     */
    @Override
    public String chatWithAi(Member member, UserMessage userMessage) {
        List<String> charactersNameByMemberId = memberService.getCharactersNameByMemberId(
                member.getId());

        String systemPrompt = promptBuilder.buildSystemPromptWithChat(member,
                charactersNameByMemberId);

        return aiClient.sendChatPrompt(systemPrompt, userMessage.message());
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

        List<String> charactersNameByMemberId = memberService.getCharactersNameByMemberId(
                member.getId());

        // 사용자의 정보가 부족하면 추천해줄 수 없음.
        if (Objects.isNull(member.getMbti()) || charactersNameByMemberId.isEmpty()) {
            throw new IllegalStateException("[ERROR] 사용자의 정보가 부족하여 추천할 수 없습니다.");
        }

        String systemPrompt = promptBuilder.buildSystemPromptWithJSON(member,
                charactersNameByMemberId);
        String userPrompt = promptBuilder.buildRecommendationUserPrompt(places);

        String result = aiClient.sendJsonPrompt(systemPrompt, userPrompt);

        log.debug("AI 필터 결과: {}", result);

        try {
            return objectMapper.readValue(result.trim(), new TypeReference<List<Document>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("AI 응답 파싱 실패, 원본 응답: {}", result, e);
            throw new RuntimeException("AI 필터링 결과를 파싱하는 중 오류가 발생했습니다.", e);
        }
    }
}
