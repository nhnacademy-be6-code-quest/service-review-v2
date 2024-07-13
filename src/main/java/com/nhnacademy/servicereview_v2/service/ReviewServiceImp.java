package com.nhnacademy.servicereview_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.servicereview_v2.client.ImageClient;
import com.nhnacademy.servicereview_v2.dto.request.ImageUploadParamsRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.dto.response.ReviewInfoResponseDto;
import com.nhnacademy.servicereview_v2.entity.Review;
import com.nhnacademy.servicereview_v2.exception.ExistReviewException;
import com.nhnacademy.servicereview_v2.exception.ImageUploadFailException;
import com.nhnacademy.servicereview_v2.repository.ReviewRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public String writeReview(ReviewWriteRequestDto reviewWriteRequestDto, Long clientId) {
        log.info("Review write request: {}", reviewWriteRequestDto);
        if (reviewRepository.findByProductOrderDetailIdAndClientId(reviewWriteRequestDto.getProductOrderDetailId(), clientId) != null) {
            throw new ExistReviewException("Review already exist");
        }
        reviewRepository.save(Review.builder()
                        .isDeleted(false)
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

    @Override
    public boolean isWrited(Long clientId, Long productOrderDetailId) {
        return reviewRepository.findByProductOrderDetailIdAndClientId(productOrderDetailId, clientId) != null;
    }

    @Override
    public Page<ReviewInfoResponseDto> myReviews(int page, int size, Long clientId) {
        log.info("myReviews page: {}", page);
        Sort sort = Sort.by(Sort.Direction.DESC, "reviewRegisterDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return reviewRepository.findByClientId(clientId, pageRequest).map(i -> ReviewInfoResponseDto.builder()
                .reviewId(i.getReviewId())
                .reviewScore(i.getReviewScore())
                .reviewContent(i.getReviewContent())
                .reviewRegisterDate(i.getReviewRegisterDate())
                .reviewLastModifyDate(i.getReviewLastModifyDate())
                .isDeleted(i.getIsDeleted())
                .clientId(i.getClientId())
                .productOrderDetailId(i.getProductOrderDetailId())
                .productId(i.getProductId())
                .build());
    }

    @Override
    public ReviewInfoResponseDto getReviewInfo(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId);
        return ReviewInfoResponseDto.builder()
                .reviewId(review.getReviewId())
                .reviewScore(review.getReviewScore())
                .reviewContent(review.getReviewContent())
                .reviewRegisterDate(review.getReviewRegisterDate())
                .reviewLastModifyDate(review.getReviewLastModifyDate())
                .isDeleted(review.getIsDeleted())
                .clientId(review.getClientId())
                .productOrderDetailId(review.getProductOrderDetailId())
                .productId(review.getProductId())
                .build();
    }

    @Override
    public String updateReview(ReviewUpdateRequestDto reviewUpdateDto) {
        log.info("Review update request: {}", reviewUpdateDto);
        Review review = reviewRepository.findByReviewId(reviewUpdateDto.getReviewId());
        review.setReviewContent(reviewUpdateDto.getReviewContent());
        review.setReviewLastModifyDate(LocalDateTime.now());
        reviewRepository.save(review);
        return "Success";
    }

    @Override
    public Double getAverageReviewScore(Long productId) {
        return reviewRepository.getAverageReviewScoreByProductId(productId);
    }

    @Override
    public Page<ReviewInfoResponseDto> productReviews(Long productId, int page, int size) {
        log.info("productReviews page: {}", page);
        Sort sort = Sort.by(Sort.Direction.DESC, "reviewRegisterDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return reviewRepository.findByProductId(productId, pageRequest).map(i -> ReviewInfoResponseDto.builder()
                .reviewId(i.getReviewId())
                .reviewScore(i.getReviewScore())
                .reviewContent(i.getReviewContent())
                .reviewRegisterDate(i.getReviewRegisterDate())
                .reviewLastModifyDate(i.getReviewLastModifyDate())
                .isDeleted(i.getIsDeleted())
                .clientId(i.getClientId())
                .productOrderDetailId(i.getProductOrderDetailId())
                .productId(i.getProductId())
                .build());
    }
}
