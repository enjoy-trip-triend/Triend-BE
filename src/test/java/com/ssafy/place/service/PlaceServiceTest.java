package com.ssafy.place.service;

import com.ssafy.member.dto.CharacterDTO;
import com.ssafy.member.dto.Member;
import com.ssafy.member.mapper.MemberMapper;
import com.ssafy.place.dto.PlaceResponseDto;
import com.ssafy.place.mapper.PlaceMapper;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import org.springframework.security.core.parameters.P;

import static com.ssafy.common.constant.QueryLimitConstants.DEFAULT_LIMIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceMapper placeMapper;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private PlaceServiceImpl placeService;

    private Member member;
    private PlaceResponseDto placeResponseDto;

    @BeforeEach
    void setup() {
        member = new Member();
        member.setId(1L);
        member.setMbti("ENTP");

        placeResponseDto = new PlaceResponseDto();
        placeResponseDto.setId(100L);
        placeResponseDto.setKakaoId(999L);
        placeResponseDto.setPlaceName("Test Place");
        placeResponseDto.setAddressName("123 Test St");
        placeResponseDto.setRoadAddressName("Test Road 123");
        placeResponseDto.setLatitude(new BigDecimal("37.1234567"));
        placeResponseDto.setLongitude(new BigDecimal("127.1234567"));
        placeResponseDto.setPhone("010-1234-5678");
        placeResponseDto.setSaveCount(5L);
        placeResponseDto.setCategoryId(10L);
        placeResponseDto.setCategoryName("TestCategory");
    }

    @Test
    @DisplayName("MBTI가 없으면 빈리스트를 반환하고 mapper는 호출되지 않는다.")
    void mbtiBlankShouldReturnEmptyList() {
        // given
        member.setMbti("");

        // when
        List<PlaceResponseDto> result = placeService.getTopPlacesByMbti(member);

        // then
        assertThat(result).isEmpty();
        verify(placeMapper, never()).selectTopPlacesByMbti(anyString(), anyLong(), anyInt());
    }

    @Test
    @DisplayName("MBTI가 NULL이면 빈리스트를 반환하고 mapper는 호출되지 않는다.")
    void mbtiNullShouldReturnEmptyList() {
        // given
        member.setMbti(null);

        // when
        List<PlaceResponseDto> result = placeService.getTopPlacesByMbti(member);

        // then
        assertThat(result).isEmpty();
        verify(placeMapper, never()).selectTopPlacesByMbti(anyString(), anyLong(), anyInt());
    }

    @Test
    @DisplayName("유효한 MBTI면 mapper 호출 결과를 그대로 반환한다")
    void validMbtiShouldReturnMapperResults() {
        // given

        when(placeMapper.selectTopPlacesByMbti("ENTP", 1L, DEFAULT_LIMIT)).thenReturn(
                List.of(placeResponseDto));

        // when
        List<PlaceResponseDto> result = placeService.getTopPlacesByMbti(member);

        // then
        verify(placeMapper).selectTopPlacesByMbti("ENTP", 1L, DEFAULT_LIMIT);
        assertThat(result).hasSize(1)
                .first()
                .extracting(PlaceResponseDto::getPlaceName,
                        PlaceResponseDto::getCategoryName,
                        PlaceResponseDto::getSaveCount)
                .containsExactly("Test Place", "TestCategory", 5L);
    }

    @Test
    @DisplayName("characterList가 비어 있으면 빈 리스트 반환하고 placeMapper 호출 안 함")
    void emptyCharacterListShouldReturnEmptyList() {
        // given
        when(memberMapper.selectCharactersById(1L)).thenReturn(Collections.emptyList());

        // when
        List<PlaceResponseDto> result = placeService.getTopPlacesByCharacter(member);

        // then
        assertThat(result).isEmpty();
        verify(memberMapper).selectCharactersById(1L);
        verify(placeMapper, never()).selectTopPlacesByCharacter(anyList(), anyLong(), anyInt());
    }

    @Test
    @DisplayName("characterList가 있으면 해당 IDs로 placeMapper 호출 후 결과 반환")
    void nonEmptyCharacterListShouldReturnCallMapperAndReturnResults() {
        // given
        List<CharacterDTO> characterDTOList = List.of(
                new CharacterDTO(1L, "testCharacter1"),
                new CharacterDTO(2L, "testCharacter1")
        );
        when(memberMapper.selectCharactersById(1L)).thenReturn(characterDTOList);
        when(placeMapper.selectTopPlacesByCharacter(List.of(1L, 2L), 1L, DEFAULT_LIMIT)).thenReturn(
                List.of(placeResponseDto));

        // when
        List<PlaceResponseDto> result = placeService.getTopPlacesByCharacter(member);

        // then
        assertThat(result).containsExactly(placeResponseDto);
        assertThat(result).hasSize(1);
        verify(memberMapper).selectCharactersById(1L);
        verify(placeMapper).selectTopPlacesByCharacter(List.of(1L, 2L), 1L,
                DEFAULT_LIMIT);
    }

}