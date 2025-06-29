package com.ssafy.place.controller;

import static com.ssafy.common.constant.QueryLimitConstants.*;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import com.ssafy.place.dto.PlaceResponseDto;
import com.ssafy.place.service.PlaceService;
import feign.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    /**
     * 로그인한 사용자와 같은 MBTI를 가진 사용자들이 가장 많이 저장한 장소 목록을 조회합니다.
     *
     * @param customUserDetails 로그인한 사용자
     * @return 저장 횟수(saveCount) 내림차순으로 정렬된 장소 목록을 담은 {@code ResponseEntity<List<PlaceResponseDto>>}
     */
    @GetMapping("/mbti/top")
    public ResponseEntity<List<PlaceResponseDto>> getTopPlacesByMbti(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        List<PlaceResponseDto> topPlacesByMbti = placeService.getTopPlacesByMbti(member);
        return ResponseEntity.ok(topPlacesByMbti);
    }

    /**
     * 로그인한 사용자와 같은 성향을 가진 사용자들이 가장 많이 저장한 장소 목록을 조회합니다.
     *
     * @param customUserDetails 로그인한 사용자
     * @return 저장 횟수(saveCount) 내림차순으로 정렬된 장소 목록을 담은 {@code ResponseEntity<List<PlaceResponseDto>>}
     */
    @GetMapping("/characters/top")
    public ResponseEntity<List<PlaceResponseDto>> getTopPlacesByCharacters(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        List<PlaceResponseDto> topPlacesByCharacter = placeService.getTopPlacesByCharacter(member);
        return ResponseEntity.ok(topPlacesByCharacter);
    }

}
