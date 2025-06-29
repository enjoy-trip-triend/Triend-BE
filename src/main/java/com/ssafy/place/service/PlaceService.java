package com.ssafy.place.service;

import com.ssafy.member.dto.Member;
import com.ssafy.place.dto.PlaceResponseDto;
import java.util.List;

public interface PlaceService {

    List<PlaceResponseDto> getTopPlacesByMbti(Member member);

    List<PlaceResponseDto> getTopPlacesByCharacter(Member member);
}
