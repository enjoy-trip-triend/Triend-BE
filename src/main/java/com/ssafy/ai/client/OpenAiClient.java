package com.ssafy.ai.client;

import static com.ssafy.ai.constant.PromptParamConstants.*;

import com.ssafy.ai.tool.MemberTool;
import com.ssafy.ai.tool.PlaceRecommendationTool;
import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiClient implements AiClient {

    @Value("${ssafy.ai.system-prompt}")
    private String systemPrompt;

    private final ChatClient chatClient;
    private final MemberService memberService;
    private final MemberTool memberTool;
    private final PlaceRecommendationTool placeRecommendationTool;

    @Override
    public String sendPrompt(Member member, String message) {
        List<CharacterDTO> charactersDtoList = memberService.getCharacterByMemberId(member.getId());
        List<String> characters = charactersDtoList.stream().map(CharacterDTO::getName).toList();

        return chatClient.prompt()
                .system(spec -> spec.text(systemPrompt)
                        .param("email", member.getEmail())
                        .param("name", member.getName())
                        .param("mbti", member.getMbti())
                        .param("age", getAge(member.getBirth()))
                        .param("traits", String.join(", ", characters))
                        .param("language", LANGUAGE)
                        .param("character", CHARACTER))
                .user(message)
                .tools(memberTool, placeRecommendationTool)
                .call()
                .content();
    }

    private int getAge(LocalDate birth) {
        return LocalDate.now().getYear() - birth.getYear();
    }
}
