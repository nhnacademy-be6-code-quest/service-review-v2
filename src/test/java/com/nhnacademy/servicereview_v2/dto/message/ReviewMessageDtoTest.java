package com.nhnacademy.servicereview_v2.dto.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewMessageDtoTest {

    @Test
    void testReviewMessageDto() {
        Long clientId = 100L;
        Boolean hasImage = true;

        ReviewMessageDto reviewMessageDto = new ReviewMessageDto(clientId, hasImage);

        // Verify that the values are correctly assigned via constructor
        assertNotNull(reviewMessageDto);
        assertEquals(clientId, reviewMessageDto.getClientId());
        assertEquals(hasImage, reviewMessageDto.getHasImage());
    }
}