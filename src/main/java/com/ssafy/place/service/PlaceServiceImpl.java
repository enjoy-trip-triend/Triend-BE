package com.ssafy.place.service;

import static com.ssafy.common.constant.QueryLimitConstants.*;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.mapper.MemberMapper;
import com.ssafy.place.dto.PlaceResponseDto;
import com.ssafy.place.mapper.PlaceMapper;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceMapper placeMapper;
    private final MemberMapper memberMapper;

    /**
     * 현재 로그인한 멤버와 동일한 MBTI를 가진 사용자들이 가장 많이 저장한 장소 목록 반환
     *
     * @param member 현재 로그인한 멤버
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> getTopPlacesByMbti(Member member) {

        if (!StringUtils.hasText(member.getMbti())) {
            return Collections.emptyList();
        }

        return placeMapper.selectTopPlacesByMbti(member.getMbti(), member.getId(), DEFAULT_LIMIT);
    }

    /**
     * 현재 로그인한 멤버와 동일한 성향을 가진 사용자들이 가장 많이 저장한 장소 목록 반환
     *
     * @param member 현재 로그인한 멤버
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<PlaceResponseDto> getTopPlacesByCharacter(Member member) {

        List<CharacterDTO> characterList = memberMapper.selectCharactersById(member.getId());
        List<Long> characterIds = characterList.stream()
                .map(CharacterDTO::getId)
                .toList();

        return placeMapper.selectTopPlacesByCharacter(characterIds, member.getId(), DEFAULT_LIMIT);
    }
}
