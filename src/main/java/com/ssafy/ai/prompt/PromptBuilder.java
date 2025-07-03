package com.ssafy.ai.prompt;

import com.ssafy.client.kakao.dto.KakaoSearchResponseDto;
import com.ssafy.member.dto.Member;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    /**
     * ai가 추천 장소를 JSON 배열 형식으로 응답하도록 지시하는 프롬프트를 생성합니다.
     *
     * @param places 카카오 맵에서 조회된 장소 후보
     * @return AI에게 보낼 프롬프트
     */
    public String buildRecommendationPrompt(List<KakaoSearchResponseDto.Document> places) {
        StringBuilder sb = new StringBuilder();
        sb.append("아래는 후보 관광지 목록입니다. 이 중에서 사용자에게 가장 추천할 장소만 JSON 배열 형식으로 응답해 주세요.\n");
        sb.append(
                "응답에는 다음 필드를 포함해야 합니다: id, place_name, category_name, address_name, road_address_name, phone, category_group_code, category_group_name, x, y\n");
        sb.append("--- 목록 시작 ---\n");
        sb.append("[");

        for (int i = 0; i < places.size(); i++) {
            KakaoSearchResponseDto.Document p = places.get(i);
            sb.append("{\n");
            sb.append("  \"id\": \"")
                    .append(p.getKakaoId())
                    .append("\",");
            sb.append("  \"place_name\": \"")
                    .append(p.getPlaceName())
                    .append("\", ");
            sb.append("  \"category_name\": \"")
                    .append(p.getCategoryName())
                    .append("\", ");
            sb.append("  \"address_name\": \"")
                    .append(p.getAddressName())
                    .append("\", ");
            sb.append("  \"road_address_name\": \"")
                    .append(p.getRoadAddressName())
                    .append("\", ");
            sb.append("  \"phone\": \"")
                    .append(p.getPhone())
                    .append("\", ");
            sb.append("  \"category_group_code\": \"")
                    .append(p.getCategoryGroupCode())
                    .append("\", ");
            sb.append("  \"category_group_name\": \"")
                    .append(p.getCategoryGroupName())
                    .append("\", ");
            sb.append("  \"x\": \"")
                    .append(p.getLongitude())
                    .append("\", ");
            sb.append("  \"y\": \"")
                    .append(p.getLatitude())
                    .append("\"\n");
            sb.append("}");
            if (i < places.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("]\n");
        sb.append("--- 목록 끝 ---");
        return sb.toString();
    }

}
