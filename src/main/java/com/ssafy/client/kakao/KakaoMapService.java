package com.ssafy.client.kakao;

import com.ssafy.client.kakao.dto.KakaoSearchResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoMapService {
    private final KakaoMapFeignClient kakaoMapFeignClient;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    /**
     *
     * @param keyword 지역기반 키워드
     * @param categoryGroupCode 카테고리 그룹 코드
     * @param page 페이지 번호
     * @param size 페이지당 조회 개수
     * @return 카카오 응답의 Document 리스트
     */
    public List<KakaoSearchResponse.Document> searchPlacesByKeyword(
            String keyword,
            CategoryGroupCode categoryGroupCode,
            Integer page,
            Integer size
    ) {

        // 파라미터 기본 값 처리
        CategoryGroupCode cat = (categoryGroupCode != null) ? categoryGroupCode : CategoryGroupCode.AT4;
        int p  = (page != null && page > 0) ? page : 1;
        int s = (size != null && size > 0 && size <= 10) ? size : 10;

        // 카카오 인증 헤더
        String authHeader = "KakaoAK " + kakaoApiKey;

        log.debug("카카오 인증 헤더: {}", authHeader);


        // 카카오맵 api 응답 결과
        KakaoSearchResponse resp = kakaoMapFeignClient.searchByKeyword(
                authHeader,
                keyword + " 관광지",
                cat,
                p,
                s
        );

        return resp.getDocuments();

    }
}
