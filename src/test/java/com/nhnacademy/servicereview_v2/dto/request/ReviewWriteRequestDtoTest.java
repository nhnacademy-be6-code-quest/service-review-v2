package com.nhnacademy.servicereview_v2.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewWriteRequestDtoTest {

    @Test
    void testBuilderAndAccessors() {
        Long productId = 100L;
        Long productOrderDetailId = 200L;
        byte reviewScore = 5;
        String reviewContent = "Excellent product, highly recommend!";

        ReviewWriteRequestDto dto = ReviewWriteRequestDto.builder()
                .productId(productId)
                .productOrderDetailId(productOrderDetailId)
                .reviewScore(reviewScore)
                .reviewContent(reviewContent)
                .build();

        // Test builder outputs and getters
        assertThat(dto.getProductId()).isEqualTo(productId);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(productOrderDetailId);
        assertThat(dto.getReviewScore()).isEqualTo(reviewScore);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);

        // Testing toString
        assertThat(dto.toString()).contains(
                "ReviewWriteRequestDto(productId=" + productId,
                "productOrderDetailId=" + productOrderDetailId,
                "reviewScore=" + reviewScore,
                "reviewContent=" + reviewContent
        );

        // Test setters
        dto.setProductId(101L);
        dto.setProductOrderDetailId(201L);
        dto.setReviewScore((byte) 4);
        dto.setReviewContent("Good product, with some minor issues.");

        assertThat(dto.getProductId()).isEqualTo(101L);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(201L);
        assertThat(dto.getReviewScore()).isEqualTo((byte) 4);
        assertThat(dto.getReviewContent()).isEqualTo("Good product, with some minor issues.");
    }

    @Test
    void testNoArgsConstructor() {
        ReviewWriteRequestDto dto = new ReviewWriteRequestDto();
        dto.setProductId(102L);
        dto.setProductOrderDetailId(202L);
        dto.setReviewScore((byte) 3);
        dto.setReviewContent("Average product.");

        assertThat(dto.getProductId()).isEqualTo(102L);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(202L);
        assertThat(dto.getReviewScore()).isEqualTo((byte) 3);
        assertThat(dto.getReviewContent()).isEqualTo("Average product.");
    }

    @Test
    void testAllArgsConstructor() {
        Long productId = 103L;
        Long productOrderDetailId = 203L;
        byte reviewScore = 2;
        String reviewContent = "Below average, would not recommend.";

        ReviewWriteRequestDto dto = new ReviewWriteRequestDto(productId, productOrderDetailId, reviewScore, reviewContent);

        assertThat(dto.getProductId()).isEqualTo(productId);
        assertThat(dto.getProductOrderDetailId()).isEqualTo(productOrderDetailId);
        assertThat(dto.getReviewScore()).isEqualTo(reviewScore);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);
    }
}
