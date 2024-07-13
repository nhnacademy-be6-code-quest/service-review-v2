package com.nhnacademy.servicereview_v2.controller;

import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.dto.response.ReviewInfoResponseDto;
import com.nhnacademy.servicereview_v2.exception.ExistReviewException;
import com.nhnacademy.servicereview_v2.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewControllerImp implements ReviewController {
    private final ReviewService reviewService;

    @Override
    public ResponseEntity<List<ImageUploadResponseDto.SuccessFile>> uploadImages(List<MultipartFile> files) {
        log.info("Uploading images to review service");
        return ResponseEntity.ok(reviewService.uploadImage(files));
    }

    @Override
    public ResponseEntity<ImageUploadResponseDto.SuccessFile> uploadImages(MultipartFile file) {
        log.info("Uploading image to review service");
        return ResponseEntity.ok(reviewService.uploadImage(List.of(file)).getFirst());
    }

    @Override
    public ResponseEntity<String> writeReview(ReviewWriteRequestDto reviewWriteRequestDto, String clientIdHeader) {
        log.info(reviewWriteRequestDto.toString());
        return ResponseEntity.ok(reviewService.writeReview(reviewWriteRequestDto, Long.valueOf(clientIdHeader)));
    }

    @Override
    public ResponseEntity<Boolean> isWrited(Long orderDetailId, String clientIdHeader) {
        Boolean iswrited = reviewService.isWrited(Long.valueOf(clientIdHeader), orderDetailId);
        log.info("{} : {} : {}", clientIdHeader, orderDetailId, iswrited);
        return ResponseEntity.ok(iswrited);
    }

    @Override
    public ResponseEntity<Page<ReviewInfoResponseDto>> getMyReviews(int page, int size, String clientIdHeader) {
        log.info("getMyReviews");
        return ResponseEntity.ok(reviewService.myReviews(page, size, Long.valueOf(clientIdHeader)));
    }

    @Override
    public ResponseEntity<ReviewInfoResponseDto> getReviewInfo(Long reviewId, String clientIdHeader) {
        log.info("getReviewInfo");
        return ResponseEntity.ok(reviewService.getReviewInfo(reviewId));
    }

    @Override
    public ResponseEntity<String> updateReview(ReviewUpdateRequestDto reviewUpdateDto, String userIdHeader) {
        log.info("updateReview : {}", reviewUpdateDto.getReviewId());
        return ResponseEntity.ok(reviewService.updateReview(reviewUpdateDto));
    }

    @Override
    public ResponseEntity<Double> getReviewScore(Long productId) {
        log.info("getReviewScore : {}", productId);
        return ResponseEntity.ok(reviewService.getAverageReviewScore(productId));
    }

    @Override
    public ResponseEntity<Page<ReviewInfoResponseDto>> getReviewProducts(int page, int size, Long productId) {
        log.info("getReviewProducts");
        return ResponseEntity.ok(reviewService.productReviews(productId, page, size));
    }

    @Override
    public ResponseEntity<String> existReviewException(ExistReviewException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
