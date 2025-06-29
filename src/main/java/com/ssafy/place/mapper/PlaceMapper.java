package com.ssafy.place.mapper;

import com.ssafy.place.dto.PlaceResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlaceMapper {

    // 로그인 한 사용자와 같은 mbti를 가진 사용자들이 가장 많이 담은 장소 추출
    List<PlaceResponseDto> selectTopPlacesByMbti(@Param("mbti") String mbti,
            @Param("excludeMemberId") Long excludeMemberId, @Param("limit") int limit);

    // 로그인 한 사용자와 같은 성향을 사진 사용자들이 가장 많이 담은 장소 추출
    List<PlaceResponseDto> selectTopPlacesByCharacter(
            @Param("characterIds") List<Long> characterIds,
            @Param("excludeMemberId") Long excludeMemberId,
            @Param("limit") int limit);
}
