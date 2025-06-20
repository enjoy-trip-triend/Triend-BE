package com.ssafy.client.kakao;

import com.ssafy.client.kakao.dto.KakaoSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-map", url = "${kakao.api.base-url}")
public interface KakaoMapFeignClient {
    @GetMapping("/v2/local/search/keyword.json")
    KakaoSearchResponse searchByKeyword(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("query") String query,
            @RequestParam(value = "category_group_code", required = false) CategoryGroupCode categoryGroupCode,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
