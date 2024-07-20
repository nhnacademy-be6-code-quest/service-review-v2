package com.nhnacademy.servicereview_v2.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewInfoResponseDtoTest {

    @Test
    void testGettersAndSetters() {
        Long reviewId = 1L;
        byte reviewScore = 5;
        String reviewContent = "Excellent service!";
        LocalDateTime reviewRegisterDate = LocalDateTime.now();
        LocalDateTime reviewLastModifyDate = LocalDateTime.now().plusDays(1);
        Long clientId = 100L;
        Long productOrderDetailId = 500L;
        Long productId = 1000L;
        Boolean isDeleted = false;

        ReviewInfoResponseDto dto = new ReviewInfoResponseDto();
        dto.setReviewId(reviewId);
        dto.setReviewScore(reviewScore);
        dto.setReviewContent(reviewContent);
        dto.setReviewRegisterDate(reviewRegisterDate);
        dto.setReviewLastModifyDate(reviewLastModifyDate);
        dto.setClientId(clientId);
        dto.setProductOrderDetailId(productOrderDetailId);
        dto.setProductId(productId);
        dto.setIsDeleted(isDeleted);

        assertThat(dto.getReviewId()).isEqualTo(reviewId);
        assertThat(dto.getReviewScore()).isEqualTo(reviewScore);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);
        assertThat(dto.getReviewRegisterDate()).isEqualTo(reviewRegisterDate);
        assertThat(dto.getReviewLastModifyDate()).isEqualTo(reviewLastModifyDate);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(productOrderDetailId);
        assertThat(dto.getProductId()).isEqualTo(productId);
        assertThat(dto.getIsDeleted()).isEqualTo(isDeleted);
    }

    @Test
    void testNoArgsConstructor() {
        ReviewInfoResponseDto dto = new ReviewInfoResponseDto();
        assertThat(dto).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        Long reviewId = 1L;
        byte reviewScore = 5;
        String reviewContent = "Excellent service!";
        LocalDateTime reviewRegisterDate = LocalDateTime.now();
        LocalDateTime reviewLastModifyDate = LocalDateTime.now().plusDays(1);
        Long clientId = 100L;
        Long productOrderDetailId = 500L;
        Long productId = 1000L;
        Boolean isDeleted = false;

        ReviewInfoResponseDto dto = new ReviewInfoResponseDto(reviewId, reviewScore, reviewContent, reviewRegisterDate,
                reviewLastModifyDate, clientId, productOrderDetailId, productId, isDeleted);

        assertThat(dto.getReviewId()).isEqualTo(reviewId);
        assertThat(dto.getReviewScore()).isEqualTo(reviewScore);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);
        assertThat(dto.getReviewRegisterDate()).isEqualTo(reviewRegisterDate);
        assertThat(dto.getReviewLastModifyDate()).isEqualTo(reviewLastModifyDate);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(productOrderDetailId);
        assertThat(dto.getProductId()).isEqualTo(productId);
        assertThat(dto.getIsDeleted()).isEqualTo(isDeleted);
    }

    @Test
    void testBuilder() {
        Long reviewId = 1L;
        byte reviewScore = 5;
        String reviewContent = "Excellent service!";
        LocalDateTime reviewRegisterDate = LocalDateTime.now();
        LocalDateTime reviewLastModifyDate = LocalDateTime.now().plusDays(1);
        Long clientId = 100L;
        Long productOrderDetailId = 500L;
        Long productId = 1000L;
        Boolean isDeleted = false;

        ReviewInfoResponseDto dto = ReviewInfoResponseDto.builder()
                .reviewId(reviewId)
                .reviewScore(reviewScore)
                .reviewContent(reviewContent)
                .reviewRegisterDate(reviewRegisterDate)
                .reviewLastModifyDate(reviewLastModifyDate)
                .clientId(clientId)
                .productOrderDetailId(productOrderDetailId)
                .productId(productId)
                .isDeleted(isDeleted)
                .build();

        assertThat(dto.getReviewId()).isEqualTo(reviewId);
        assertThat(dto.getReviewScore()).isEqualTo(reviewScore);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);
        assertThat(dto.getReviewRegisterDate()).isEqualTo(reviewRegisterDate);
        assertThat(dto.getReviewLastModifyDate()).isEqualTo(reviewLastModifyDate);
        assertThat(dto.getClientId()).isEqualTo(clientId);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(productOrderDetailId);
        assertThat(dto.getProductId()).isEqualTo(productId);
        assertThat(dto.getIsDeleted()).isEqualTo(isDeleted);
    }
}
