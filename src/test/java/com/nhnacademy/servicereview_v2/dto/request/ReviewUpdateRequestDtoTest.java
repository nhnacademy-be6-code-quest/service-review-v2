package com.nhnacademy.servicereview_v2.dto.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewUpdateRequestDtoTest {

    @Test
    void testBuilderAndAccessors() {
        Long reviewId = 1L;
        String reviewContent = "This is a sample review update.";

        ReviewUpdateRequestDto dto = ReviewUpdateRequestDto.builder()
                .reviewId(reviewId)
                .reviewContent(reviewContent)
                .build();

        // Test builder outputs and getters
        assertThat(dto.getReviewId()).isEqualTo(reviewId);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);

        // Test setters
        dto.setReviewId(2L);
        dto.setReviewContent("Updated review content.");

        assertThat(dto.getReviewId()).isEqualTo(2L);
        assertThat(dto.getReviewContent()).isEqualTo("Updated review content.");
    }

    @Test
    void testNoArgsConstructor() {
        ReviewUpdateRequestDto dto = new ReviewUpdateRequestDto();
        dto.setReviewId(1L);
        dto.setReviewContent("Initial content using no args constructor.");

        assertThat(dto.getReviewId()).isEqualTo(1L);
        assertThat(dto.getReviewContent()).isEqualTo("Initial content using no args constructor.");
    }

    @Test
    void testAllArgsConstructor() {
        Long reviewId = 1L;
        String reviewContent = "This is a full constructor test.";

        ReviewUpdateRequestDto dto = new ReviewUpdateRequestDto(reviewId, reviewContent);

        assertThat(dto.getReviewId()).isEqualTo(reviewId);
        assertThat(dto.getReviewContent()).isEqualTo(reviewContent);
    }
}
