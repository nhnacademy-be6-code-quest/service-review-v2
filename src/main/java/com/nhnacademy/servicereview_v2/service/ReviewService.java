package com.nhnacademy.servicereview_v2.service;

import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.dto.response.ReviewInfoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    List<ImageUploadResponseDto.SuccessFile> uploadImage(List<MultipartFile> files);
    String writeReview(ReviewWriteRequestDto reviewWriteRequestDto, Long clientId);
    boolean isWrited(Long clientId, Long productOrderDetailId);
    Page<ReviewInfoResponseDto> myReviews(int page, int size, Long clientId);
    ReviewInfoResponseDto getReviewInfo(Long reviewId);
    String updateReview(ReviewUpdateRequestDto reviewUpdateDto);
    Double getAverageReviewScore(Long productId);
    Page<ReviewInfoResponseDto> productReviews(Long productId, int page, int size);
}
