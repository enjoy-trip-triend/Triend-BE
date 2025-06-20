package com.ssafy.client.kakao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.client.kakao.dto.KakaoSearchResponse.Document;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class KakaoMapServiceIT {

    @Autowired
    private KakaoMapService kakaoMapService;


    @DisplayName("실제 API 호출 결과 테스트")
    @Test
    public void searchPlaceByKeyword() {
        // given, when
        List<Document> documents = kakaoMapService.searchPlacesByKeyword("광주광역시",
                CategoryGroupCode.AT4, 1, 5);

        log.debug("카카오 API 호출 결과: {}", documents);

        // then
        assertNotNull(documents);
        assertThat(documents.size()).isEqualTo(5);
    }

}