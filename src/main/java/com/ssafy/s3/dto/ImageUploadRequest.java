package com.ssafy.s3.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUploadRequest {
    private Long planId;
    private List<String> imageUrls;
}
