package com.ssafy.s3.service;

import com.ssafy.schedule.mapper.ScheduleMapper;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final WebClient webClient;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final ScheduleMapper scheduleMapper;
    
    public List<String> uploadExternalImagesToS3(Long planId, List<String> imageUrls) {
        List<String> imageKeys = imageUrls.parallelStream()
            .map(this::downloadAndUploadImage)
            .filter(Objects::nonNull)
            .toList();

        if (!imageKeys.isEmpty()) {
            scheduleMapper.insertScheduleImages(planId, imageKeys);
        }

        return imageKeys;
    }
    
    private String downloadAndUploadImage(String imageUrl) {
        try {
            
            byte[] imageBytes = webClient.get()
                    .uri(imageUrl)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .onErrorResume(e -> {
                        System.err.println("❌ 이미지 다운로드 실패: " + imageUrl + " => " + e.getMessage());
                        return Mono.empty();
                    })
                    .block();

            if (imageBytes == null) return null;

            String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
            if (contentType == null) contentType = "image/jpeg";

            String extension = contentType.split("/")[1];
            String key = UUID.randomUUID() + "." + extension;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));

         
            return key;
        } catch (Exception e) {
            
            return null;
        }
    }

   

    public String generatePresignedGetUrl(String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
