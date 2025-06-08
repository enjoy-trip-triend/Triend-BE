package com.ssafy.s3.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.s3.dto.ImageUploadRequest;
import com.ssafy.s3.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {
	
	private final S3Service s3Service;
	
	@PostMapping("/proxy-upload")
	public ResponseEntity<List<String>> proxyUpload(@RequestBody ImageUploadRequest request) {
	    List<String> imageKeys = s3Service.uploadExternalImagesToS3(
	        request.getPlanId(),
	        request.getImageUrls()
	    );
	    return ResponseEntity.ok(imageKeys);
	}
}
