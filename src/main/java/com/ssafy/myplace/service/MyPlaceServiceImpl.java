package com.ssafy.myplace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.myplace.dto.CategoryDTO;
import com.ssafy.myplace.dto.HotPlaceResponseDto;
import com.ssafy.myplace.dto.MyPlaceDto;
import com.ssafy.myplace.dto.MyPlaceUpdateResponseDto;
import com.ssafy.myplace.mapper.MyPlaceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPlaceServiceImpl implements MyPlaceService {
	
	private final MyPlaceMapper myPlaceMapper;

	@Override
	public int createMyPlace(Long memberId, MyPlaceDto myPlaceRequestDto) {
		
		if(myPlaceMapper.selectCountByMemberIdAndPlaceId(memberId, myPlaceRequestDto.getKakaoId()) > 0) {
			return -1;
		}
		
		return myPlaceMapper.insertMyPlace(memberId, myPlaceRequestDto);
	}

	@Override
	public List<MyPlaceDto> getMyplaceListByMemberId(Long memberId, Long categoryId) {
		return myPlaceMapper.selectMyPlacesByMemberId(memberId, categoryId);
	}

	@Override
	public List<CategoryDTO> getCategoryList() {
		return myPlaceMapper.selectCategories();
	}

	@Override
	public List<HotPlaceResponseDto> getHotPlaceList() {
		return myPlaceMapper.selectTop10ByOrderByCountDesc();
	}

	@Override
	public int removeMyPlaceByIds(List<Long> ids) {
		return myPlaceMapper.deleteMyPlaceByIds(ids);
	}

	@Override
	public int updateMyPlace(MyPlaceUpdateResponseDto dto) {
		return myPlaceMapper.updateMypalceById(dto);
	}
	

}