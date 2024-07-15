package com.nhnacademy.servicereview_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.servicereview_v2.client.ImageClient;
import com.nhnacademy.servicereview_v2.dto.request.ImageUploadParamsRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.entity.Review;
import com.nhnacademy.servicereview_v2.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    @Mock
    private ImageClient imageClient;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ReviewServiceImp reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadImage() throws JsonProcessingException {
        // 테스트에 사용할 값들을 정확히 지정
        String secretKey = "testSecretKey";
        String basePath = "testBasePath";

        // ReviewServiceImp의 필드 값 설정
        ReflectionTestUtils.setField(reviewService, "secretKey", secretKey);
        ReflectionTestUtils.setField(reviewService, "basePath", basePath);

        List<MultipartFile> files = Collections.singletonList(new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes()));

        String paramsJson = "{\"basepath\":\"testBasePath\",\"autorename\":true}";
        when(objectMapper.writeValueAsString(any(ImageUploadParamsRequestDto.class))).thenReturn(paramsJson);

        ImageUploadResponseDto responseDto = new ImageUploadResponseDto();
        responseDto.setSuccesses(Collections.singletonList(new ImageUploadResponseDto.SuccessFile()));
        ResponseEntity<ImageUploadResponseDto> mockResponse = ResponseEntity.ok(responseDto);

        // 정확한 인자로 when 설정
        when(imageClient.registerImages(secretKey, paramsJson, files)).thenReturn(mockResponse);

        List<ImageUploadResponseDto.SuccessFile> result = reviewService.uploadImage(files);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(imageClient).registerImages(secretKey, paramsJson, files);
    }

    @Test
    void testWriteReview_Exception() {
        ReviewWriteRequestDto requestDto = new ReviewWriteRequestDto(1L, 1L, (byte) 5, "Great review!");
        when(reviewRepository.findByProductOrderDetailIdAndClientId(anyLong(), anyLong())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> reviewService.writeReview(requestDto, 1L));
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void testIsWrited() {
        Long clientId = 1L;
        Long detailId = 1L;
        when(reviewRepository.findByProductOrderDetailIdAndClientId(detailId, clientId)).thenReturn(new Review());

        boolean result = reviewService.isWrited(clientId, detailId);
        assertTrue(result);
    }

    @Test
    void testGetAverageReviewScore() {
        Long productId = 1L;
        when(reviewRepository.getAverageReviewScoreByProductId(productId)).thenReturn(4.5);

        Double score = reviewService.getAverageReviewScore(productId);
        assertEquals(4.5, score);
    }

    // Additional method tests go here...

    // Example of testing update and delete operations
    @Test
    void testUpdateReview() {
        ReviewUpdateRequestDto updateDto = new ReviewUpdateRequestDto(1L, "Updated content");
        Review existingReview = new Review();
        existingReview.setReviewId(1L);
        existingReview.setReviewContent("Old content");

        when(reviewRepository.findByReviewId(1L)).thenReturn(existingReview);

        String result = reviewService.updateReview(updateDto);
        assertEquals("Success", result);
        assertEquals("Updated content", existingReview.getReviewContent());
        verify(reviewRepository, times(1)).save(existingReview);
    }
}
