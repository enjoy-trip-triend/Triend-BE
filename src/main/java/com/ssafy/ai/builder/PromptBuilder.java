package com.ssafy.ai.builder;

import static com.ssafy.ai.constant.PromptParamConstants.*;

import com.ssafy.client.kakao.dto.KakaoSearchResponseDto;
import com.ssafy.member.dto.Member;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    @Value("${triend.ai.system-prompt.base}")
    private String base;
    @Value("${triend.ai.system-prompt.json-suffix}")
    private String jsonSuffix;

    @Value("${triend.ai.system-prompt.chat-suffix}")
    private String chatSuffix;

    public String buildSystemPromptWithChat(Member member, List<String> traits) {
        return populateBase(member, traits) + "\n\n" + chatSuffix.replace("{character}", CHARACTER)
                .replace("{language}", LANGUAGE);
    }

    /**
     * JSON 응답을 받을 prompt를 생성합니다.
     *
     * @param member 현재 로그인 한 멤버
     * @param traits 현재 로그인 한 멤버의 성향
     * @return JSON 응답용 systemPrompt 생성
     */
    public String buildSystemPromptWithJSON(Member member, List<String> traits) {
        return populateBase(member, traits) + "\n\n" + jsonSuffix;
    }

    /**
     * ai가 추천 장소를 응답하도록 지시하는 사용자 프롬프트를 생성합니다.
     *
     * @param places 카카오 맵에서 조회된 장소 후보
     * @return AI에게 보낼 프롬프트
     */
    public String buildRecommendationUserPrompt(List<KakaoSearchResponseDto.Document> places) {
        StringBuilder sb = new StringBuilder();

        // 1) 정상 응답 스키마
        sb.append("응답 형식:\n");
        sb.append("1) 성공 시: 최대 10개의 객체를 요소로 가지는 JSON 배열\n");
        sb.append("   각 객체는 반드시 다음 필드를 포함해야 합니다:\n");
        sb.append("     - id (string)\n");
        sb.append("     - place_name (string)\n");
        sb.append("     - category_name (string)\n");
        sb.append("     - address_name (string)\n");
        sb.append("     - road_address_name (string)\n");
        sb.append("     - phone (string)\n");
        sb.append("     - category_group_code (string)\n");
        sb.append("     - category_group_name (string)\n");
        sb.append("     - x (string)\n");
        sb.append("     - y (string)\n");
        sb.append("2) 오류 시: { \"error\": \"오류 메세지\" } 형태의 단일 JSON 객체만 반환하세요.\n\n");

        // 2) 실제 후보 리스트
        sb.append("아래는 후보 관광지 목록입니다. 이 중에서 사용자에게 가장 추천할 장소 10개를 선택해 주세요.\n");
        sb.append("[\n");
        for (int i = 0; i < places.size(); i++) {
            KakaoSearchResponseDto.Document p = places.get(i);
            sb.append("  {\n");
            sb.append("    \"id\": \"")
                    .append(p.getKakaoId())
                    .append("\",\n");
            sb.append("    \"place_name\": \"")
                    .append(p.getPlaceName())
                    .append("\",\n");
            sb.append("    \"category_name\": \"")
                    .append(p.getCategoryName())
                    .append("\",\n");
            sb.append("    \"address_name\": \"")
                    .append(p.getAddressName())
                    .append("\",\n");
            sb.append("    \"road_address_name\": \"")
                    .append(p.getRoadAddressName())
                    .append("\",\n");
            sb.append("    \"phone\": \"")
                    .append(p.getPhone())
                    .append("\",\n");
            sb.append("    \"category_group_code\": \"")
                    .append(p.getCategoryGroupCode())
                    .append("\",\n");
            sb.append("    \"category_group_name\": \"")
                    .append(p.getCategoryGroupName())
                    .append("\",\n");
            sb.append("    \"x\": \"")
                    .append(p.getLongitude())
                    .append("\",\n");
            sb.append("    \"y\": \"")
                    .append(p.getLatitude())
                    .append("\"\n");
            sb.append("  }");
            if (i < places.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

    private String populateBase(Member member, List<String> traits) {
        return base.replace("{email}", member.getEmail())
                .replace("{name}", member.getName())
                .replace("{mbti}", member.getMbti())
                .replace("{age}", String.valueOf(calculateAge(member.getBirth())))
                .replace("{traits}", String.join(", ", traits));
    }

    private int calculateAge(LocalDate birth) {
        return LocalDate.now()
                .getYear() - birth.getYear();
    }
}
