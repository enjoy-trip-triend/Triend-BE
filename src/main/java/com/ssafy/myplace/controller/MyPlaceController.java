package com.ssafy.myplace.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.security.dto.CustomUserDetails;
import com.ssafy.member.dto.Member;
import com.ssafy.myplace.dto.CategoryDTO;
import com.ssafy.myplace.dto.HotPlaceResponseDto;
import com.ssafy.myplace.dto.MyPlaceDto;
import com.ssafy.myplace.dto.MyPlaceUpdateResponseDto;
import com.ssafy.myplace.service.MyPlaceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/myplace")
@RequiredArgsConstructor
@Slf4j
public class MyPlaceController {

	private final MyPlaceService myPlaceService;

	@PostMapping("/create-myplace")
	public ResponseEntity<Void> createMyPlace(@RequestBody MyPlaceDto myPlaceRequestDto,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		log.debug("createMyPlace Method");
		log.info("myplace-request: {}", myPlaceRequestDto);
		Member member = userDetails.getMember();
		int result = myPlaceService.createMyPlace(member.getId(), myPlaceRequestDto);
		if(result == -1) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/get-myplace-list")
	public ResponseEntity<List<MyPlaceDto>> getMyPlaceList(
			@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(required = false) Long categoryId) {
		log.debug("getMyPlaceList Method");
		Long memberId = userDetails.getMember().getId();
		List<MyPlaceDto> body = myPlaceService.getMyplaceListByMemberId(memberId, categoryId);

		log.debug("내 장소 리스트 (memberId={}): {}", memberId, body);

		return ResponseEntity.ok(body);
	}
	
	@GetMapping("/get-hotplace-list")
	public ResponseEntity<List<HotPlaceResponseDto>> getHotPlaceList() {
		List<HotPlaceResponseDto> body = myPlaceService.getHotPlaceList();
		return ResponseEntity.ok(body);
	}
	
	@GetMapping("/get-category-list")
	public ResponseEntity<List<CategoryDTO>> getCategoryList() {
		List<CategoryDTO> body = myPlaceService.getCategoryList();
		return ResponseEntity.ok(body);
	}

	@PostMapping("/delete-myplace")
	public ResponseEntity<Void> deleteMyPlace(@RequestBody List<Long> ids) {
		myPlaceService.removeMyPlaceByIds(ids);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/update-myplace")
	public ResponseEntity<Void> updateMyPlace(@RequestBody MyPlaceUpdateResponseDto dto) {
		myPlaceService.updateMyPlace(dto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
