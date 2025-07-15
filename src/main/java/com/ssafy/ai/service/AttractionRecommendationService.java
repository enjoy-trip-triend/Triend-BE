package com.ssafy.ai.service;

import static com.ssafy.client.kakao.constant.KakaoApiConstants.*;

import com.ssafy.client.kakao.CategoryGroupCode;
import com.ssafy.client.kakao.KakaoMapService;
import com.ssafy.client.kakao.dto.KakaoSearchResponseDto.Document;
import com.ssafy.member.dto.Member;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionRecommendationService {

    private static final Duration TTL = Duration.ofDays(7);
    private static final String KEY_PREFIX = "place:region:";
    private final KakaoMapService kakaoMapService;
    private final AIService aiService;
    private final Executor kakaoTaskExecutor;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 사용자에게 맞춤형 관광지를 추천해줍니다.
     *
     * @param member  현재 로그인한 멤버
     * @param regions 사용자가 플래너에 담은 지역
     * @return 사용자 맞춤 관광지 장소 반환
     */
    public List<Document> recommendAttraction(Member member, List<String> regions) {

        if (regions == null || regions.isEmpty()) {
            return Collections.emptyList();
        }

        List<CompletableFuture<List<Document>>> futures = regions.stream()
                .map(region -> CompletableFuture.supplyAsync(
                                () -> {
                                    String key = KEY_PREFIX + region;
                                    // 내가 이 unchecked 캐스트를 알아서 할 테니 경고 내지 말아라
                                    @SuppressWarnings("unchecked")
                                    List<Document> value = (List<Document>) redisTemplate.opsForValue()
                                            .get(key);

                                    if (!value.isEmpty()) {
                                        return value;
                                    }

                                    List<Document> documents = kakaoMapService.searchPlacesByKeyword(
                                            region, CategoryGroupCode.AT4, DEFAULT_PAGE,
                                            DEFAULT_SIZE);

                                    redisTemplate.opsForValue().set(key, documents, TTL);
                                    return documents;
                                }, kakaoTaskExecutor)
                        .orTimeout(2, TimeUnit.SECONDS)
                        .exceptionally(ex -> {
                            throw new RuntimeException("[ERROR] 장소를 가져오는데 에러가 발생했습니다.");
                        }))
                .toList();

        CompletableFuture<Void> allDone = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0]));

        List<Document> documents = allDone.thenApply(v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .distinct()
                        .toList())
                .join();

        log.debug("documents: {}", documents);

        if (documents.isEmpty()) {
            return Collections.emptyList();
        }

        return aiService.filterAttractionsByAi(member, documents);
    }

}
