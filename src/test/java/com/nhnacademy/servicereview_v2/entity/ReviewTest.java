package com.nhnacademy.servicereview_v2.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {
    @Test
    void testNoArgsConstructor() {
        Review review = new Review();
        assertThat(review).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Review review = new Review(1L, (byte) 5, "Excellent product!", now, now, 1L, 1L, 1L, false);

        assertThat(review.getReviewId()).isEqualTo(1L);
        assertThat(review.getReviewScore()).isEqualTo((byte) 5);
        assertThat(review.getReviewContent()).isEqualTo("Excellent product!");
        assertThat(review.getReviewRegisterDate()).isEqualTo(now);
        assertThat(review.getReviewLastModifyDate()).isEqualTo(now);
        assertThat(review.getClientId()).isEqualTo(1L);
        assertThat(review.getProductOrderDetailId()).isEqualTo(1L);
        assertThat(review.getProductId()).isEqualTo(1L);
        assertThat(review.getIsDeleted()).isFalse();
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Review review = Review.builder()
                .reviewId(1L)
                .reviewScore((byte) 5)
                .reviewContent("Great service!")
                .reviewRegisterDate(now)
                .reviewLastModifyDate(now)
                .clientId(2L)
                .productOrderDetailId(2L)
                .productId(2L)
                .isDeleted(true)
                .build();

        assertThat(review.getReviewId()).isEqualTo(1L);
        assertThat(review.getReviewScore()).isEqualTo((byte) 5);
        assertThat(review.getReviewContent()).isEqualTo("Great service!");
        assertThat(review.getReviewRegisterDate()).isEqualTo(now);
        assertThat(review.getReviewLastModifyDate()).isEqualTo(now);
        assertThat(review.getClientId()).isEqualTo(2L);
        assertThat(review.getProductOrderDetailId()).isEqualTo(2L);
        assertThat(review.getProductId()).isEqualTo(2L);
        assertThat(review.getIsDeleted()).isTrue();
    }

    @Test
    void testSetters() {
        LocalDateTime now = LocalDateTime.now();
        Review review = new Review();
        review.setReviewId(3L);
        review.setReviewScore((byte) 4);
        review.setReviewContent("Good enough.");
        review.setReviewRegisterDate(now);
        review.setReviewLastModifyDate(now);
        review.setClientId(3L);
        review.setProductOrderDetailId(3L);
        review.setProductId(3L);
        review.setIsDeleted(false);

        assertThat(review.getReviewId()).isEqualTo(3L);
        assertThat(review.getReviewScore()).isEqualTo((byte) 4);
        assertThat(review.getReviewContent()).isEqualTo("Good enough.");
        assertThat(review.getReviewRegisterDate()).isEqualTo(now);
        assertThat(review.getReviewLastModifyDate()).isEqualTo(now);
        assertThat(review.getClientId()).isEqualTo(3L);
        assertThat(review.getProductOrderDetailId()).isEqualTo(3L);
        assertThat(review.getProductId()).isEqualTo(3L);
        assertThat(review.getIsDeleted()).isFalse();
    }
}
