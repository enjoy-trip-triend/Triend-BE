package com.ssafy.ai.service;

import com.ssafy.ai.dto.UserMessage;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.member.dto.Member;
import java.util.List;

public interface AiService {

    String chatWithAi(Member member, UserMessage userMessage);

    List<Document> filterAttractionsByAi(Member member, List<Document> places);
}
