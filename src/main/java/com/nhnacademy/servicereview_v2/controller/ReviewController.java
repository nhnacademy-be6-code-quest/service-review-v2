package com.nhnacademy.servicereview_v2.controller;

import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.dto.response.ReviewInfoResponseDto;
import com.nhnacademy.servicereview_v2.exception.ExistReviewException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewController {
    @PostMapping(path = "/api/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<ImageUploadResponseDto.SuccessFile>> uploadImages(@RequestPart("files") List<MultipartFile> files);

    @PostMapping(path = "/api/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ImageUploadResponseDto.SuccessFile> uploadImages(@RequestPart("file") MultipartFile file);

    @PostMapping("/api/review")
    ResponseEntity<String> writeReview(
            @RequestBody ReviewWriteRequestDto reviewWriteRequestDto,
            @RequestHeader("X-User-Id") String userIdHeader
    );

    @GetMapping("/api/review/{orderDetailId}")
    ResponseEntity<Boolean> isWrited(
            @PathVariable Long orderDetailId,
            @RequestHeader("X-User-Id") String clientIdHeader
    );

    @GetMapping("/api/reviews/my")
    ResponseEntity<Page<ReviewInfoResponseDto>> getMyReviews(
            @RequestParam int page,
            @RequestParam int size,
            @RequestHeader("X-User-Id") String clientIdHeader
    );

    @GetMapping("/api/review")
    ResponseEntity<ReviewInfoResponseDto> getReviewInfo(
            @RequestParam Long reviewId,
            @RequestHeader("X-User-Id") String clientIdHeader
    );

    @PutMapping("/api/review")
    ResponseEntity<String> updateReview(
            @RequestBody ReviewUpdateRequestDto reviewUpdateDto,
            @RequestHeader("X-User-Id") String userIdHeader
    );

    @GetMapping("/api/review/score")
    ResponseEntity<Double> getReviewScore(
            @RequestParam Long productId
    );

    @GetMapping("/api/reviews/product")
    ResponseEntity<Page<ReviewInfoResponseDto>> getReviewProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Long productId
    );

    @ExceptionHandler(ExistReviewException.class)
    ResponseEntity<String> existReviewException(ExistReviewException e);
}
