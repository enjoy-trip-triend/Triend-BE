package com.ssafy.ai.service;

import com.ssafy.ai.dto.ChatRequest;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.member.dto.Member;
import java.util.List;

public interface AIService {

    String chatWithAi(Member member, ChatRequest chatRequest);

    List<Document> filterAttractionsByAi(Member member, List<Document> places);
}
