package com.nhnacademy.servicereview_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.servicereview_v2.client.ImageClient;
import com.nhnacademy.servicereview_v2.dto.request.ImageUploadParamsRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.entity.Review;
import com.nhnacademy.servicereview_v2.exception.ImageUploadFailException;
import com.nhnacademy.servicereview_v2.repository.ReviewImageRespository;
import com.nhnacademy.servicereview_v2.repository.ReviewRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImp implements ReviewService {
    private final ImageClient imageClient;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRespository reviewImageRespository;
    private final ObjectMapper objectMapper;

    @Value("${image.manager.secret.key}")
    private String secretKey;
    @Value("${image.manager.base.path}")
    private String basePath;

    @Override
    public List<ImageUploadResponseDto.SuccessFile> uploadImage(List<MultipartFile> files) {
        ImageUploadParamsRequestDto paramsDto = ImageUploadParamsRequestDto.builder()
                .basepath(basePath)
                .autorename(true)
                .build();

        try {
            String params = objectMapper.writeValueAsString(paramsDto);
            ResponseEntity<ImageUploadResponseDto> response = imageClient.registerImages(secretKey, params, files);
            log.info("Image upload response: {}", response.getBody());
            return response.getBody().getSuccesses();
        } catch (FeignException | JsonProcessingException e) {
            log.error(e.getMessage());
            throw new ImageUploadFailException("Image upload failed");
        }
    }

    public String writeReview(ReviewWriteRequestDto reviewWriteRequestDto, Long clientId) {
        log.info("Review write request: {}", reviewWriteRequestDto);
        reviewRepository.save(Review.builder()
                        .clientId(clientId)
                        .reviewRegisterDate(LocalDateTime.now())
                        .reviewLastModifyDate(LocalDateTime.now())
                        .productId(reviewWriteRequestDto.getProductId())
                        .reviewScore(reviewWriteRequestDto.getReviewScore())
                        .reviewContent(reviewWriteRequestDto.getReviewContent())
                        .productOrderDetailId(reviewWriteRequestDto.getProductOrderDetailId())
                .build());
        log.info("Review write Success");
        return "Success";
    }
}
