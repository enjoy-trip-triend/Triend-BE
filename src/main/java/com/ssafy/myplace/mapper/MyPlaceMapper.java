package com.ssafy.myplace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.myplace.dto.CategoryDTO;
import com.ssafy.myplace.dto.HotPlaceResponseDto;
import com.ssafy.myplace.dto.MyPlaceDto;
import com.ssafy.myplace.dto.MyPlaceUpdateResponseDto;
import com.ssafy.myplace.dto.RecommendPlaceDto;

@Mapper
public interface MyPlaceMapper {
	int insertMyPlace(@Param("memberId") Long memberId, @Param("dto") MyPlaceDto myPlaceRequestDto);
	int selectCountByMemberIdAndPlaceId(@Param("memberId") Long memberId, @Param("kakaoId") String kakaoId);
	List<MyPlaceDto> selectMyPlacesByMemberId(@Param("memberId") Long memberId, @Param("categoryId") Long categoryId);
	List<CategoryDTO> selectCategories();
	List<HotPlaceResponseDto> selectTop10ByOrderByCountDesc();
	int deleteMyPlaceByIds(List<Long> myPlaceIds);
	int updateMypalceById(@Param("dto") MyPlaceUpdateResponseDto updateResponseDto);
	List<RecommendPlaceDto> selectTopPlacesByMbti(@Param("mbti") String mbti, @Param("excludeMemberId") Long excludeMemberId, @Param("limit")  int limit);
	List<RecommendPlaceDto> selectTopPlacesByCharacters(
	        @Param("characterIds") List<Long> characterIds,
	        @Param("excludeMemberId") Long excludeMemberId,
	        @Param("limit") int limit
	    );
}
