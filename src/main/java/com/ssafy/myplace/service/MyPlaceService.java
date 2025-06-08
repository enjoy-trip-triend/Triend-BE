package com.ssafy.myplace.service;

import java.util.List;

import com.ssafy.myplace.dto.CategoryDTO;
import com.ssafy.myplace.dto.HotPlaceResponseDto;
import com.ssafy.myplace.dto.MyPlaceDto;
import com.ssafy.myplace.dto.MyPlaceUpdateResponseDto;

public interface MyPlaceService {
	int createMyPlace(Long memberId, MyPlaceDto myPlaceRequestDto);
	List<MyPlaceDto> getMyplaceListByMemberId(Long memberId, Long categoryId);
	List<CategoryDTO> getCategoryList();
	List<HotPlaceResponseDto> getHotPlaceList();
	int removeMyPlaceByIds(List<Long> ids);
	int updateMyPlace(MyPlaceUpdateResponseDto dto);
}
