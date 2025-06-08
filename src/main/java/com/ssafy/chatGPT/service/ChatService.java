package com.ssafy.chatGPT.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.ssafy.chatGPT.dto.UserMessage;
import com.ssafy.chatGPT.tool.MemberTool;
import com.ssafy.chatGPT.tool.PlaceRecommendationTool;
import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.chatGPT.constant.PromptParamConstants.*;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

	private final ChatClient advisedChatClient;
	private final MemberTool memberTool;
	private final PlaceRecommendationTool placeRecommendationTool;
	private final MemberService memberService;

	public String askChatGPT(UserMessage userMessage, Member member) {
		List<CharacterDTO> charactersDtoList = memberService.getCharacterByMemberId(member.getId());
		List<String> characters = charactersDtoList.stream().map(CharacterDTO::getName).toList();
		log.debug("member: {}", member);
		return advisedChatClient.prompt().system(spec -> spec
				.param("language", LANGUAGE)
				.param("character", CHARACTER)
				.param("name", member.getName())
				.param("mbti", member.getMbti())
				.param("age", getAge(member))
				.param("email", member.getEmail())
				.param("traits", String.join(", ", characters))
			)
				.user(userMessage.message())
				.tools(memberTool, placeRecommendationTool)
				.call().content();
	}
	
	private int getAge(Member member) {
		int brithYear = member.getBirth().getYear();
		int curYear = LocalDate.now().getYear();
		return curYear - brithYear;
	}
}
