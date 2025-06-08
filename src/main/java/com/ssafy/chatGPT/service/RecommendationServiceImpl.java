package com.ssafy.chatGPT.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.service.MemberService;
import com.ssafy.myplace.dto.RecommendPlaceDto;
import com.ssafy.myplace.mapper.MyPlaceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

	private final MyPlaceMapper myPlaceMapper;
	private final MemberService memberService;
	
	@Override
	public List<RecommendPlaceDto> recommendPlacesByMbti(Member me, int limit) {
		return myPlaceMapper.selectTopPlacesByMbti(me.getMbti(), me.getId(), limit);
	}

	@Override
	public List<RecommendPlaceDto> recommendByCharacters(Member me, int limit) {
		List<CharacterDTO> characterList = memberService.getCharacterByMemberId(me.getId());
		List<Long> characterIds = characterList.stream().map(CharacterDTO::getId).toList();
		return myPlaceMapper.selectTopPlacesByCharacters(characterIds, me.getId(), limit);
	}

	@Override
	public List<RecommendPlaceDto> recommendCombined(Member me, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
