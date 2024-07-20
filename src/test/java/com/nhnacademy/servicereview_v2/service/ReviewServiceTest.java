package com.nhnacademy.servicereview_v2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.servicereview_v2.dto.message.ReviewMessageDto;
import com.nhnacademy.servicereview_v2.dto.request.ImageUploadParamsRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.dto.response.ReviewInfoResponseDto;
import com.nhnacademy.servicereview_v2.entity.Review;
import com.nhnacademy.servicereview_v2.exception.ExistReviewException;
import com.nhnacademy.servicereview_v2.exception.ImageUploadFailException;
import com.nhnacademy.servicereview_v2.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReviewServiceImp reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(reviewService, "imageManagerAppKey", "testAppKey");
        ReflectionTestUtils.setField(reviewService, "imageManagerAppSecret", "testAppSecret");
        ReflectionTestUtils.setField(reviewService, "basePath", "testBasePath");
        ReflectionTestUtils.setField(reviewService, "reviewExchangeName", "testExchange");
        ReflectionTestUtils.setField(reviewService, "reviewRoutingKey", "testRoutingKey");
    }

    @Test
    void testUploadImage() throws JsonProcessingException {
        List<MultipartFile> files = Collections.singletonList(new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes()));
        String paramsJson = "{\"basepath\":\"testBasePath\",\"autorename\":true}";
        when(objectMapper.writeValueAsString(any(ImageUploadParamsRequestDto.class))).thenReturn(paramsJson);

        ImageUploadResponseDto responseDto = new ImageUploadResponseDto();
        responseDto.setSuccesses(Collections.singletonList(new ImageUploadResponseDto.SuccessFile()));
        ResponseEntity<ImageUploadResponseDto> mockResponse = ResponseEntity.ok(responseDto);

        when(restTemplate.exchange(anyString(), any(), any(), eq(ImageUploadResponseDto.class))).thenReturn(mockResponse);

        List<ImageUploadResponseDto.SuccessFile> result = reviewService.uploadImage(files);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(restTemplate).exchange(anyString(), any(), any(), eq(ImageUploadResponseDto.class));
    }

    @Test
    void testUploadImageFail() throws JsonProcessingException {
        List<MultipartFile> files = Collections.singletonList(new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes()));
        when(objectMapper.writeValueAsString(any(ImageUploadParamsRequestDto.class))).thenReturn("{}");
        when(restTemplate.exchange(anyString(), any(), any(), eq(ImageUploadResponseDto.class))).thenThrow(new RuntimeException("Upload failed"));

        assertThrows(ImageUploadFailException.class, () -> reviewService.uploadImage(files));
    }

    @Test
    void testWriteReview() {
        ReviewWriteRequestDto requestDto = new ReviewWriteRequestDto(1L, 1L, (byte) 5, "Great review!");
        when(reviewRepository.findByProductOrderDetailIdAndClientId(anyLong(), anyLong())).thenReturn(null);
        when(reviewRepository.save(any(Review.class))).thenReturn(new Review());

        String result = reviewService.writeReview(requestDto, 1L);

        assertEquals("Success", result);
        verify(reviewRepository).save(any(Review.class));
        verify(rabbitTemplate).convertAndSend(anyString(), anyString(), any(ReviewMessageDto.class));
    }
    @Test
    void testWriteReviewExistingReview() {
        ReviewWriteRequestDto requestDto = new ReviewWriteRequestDto(1L, 1L, (byte) 5, "Great review!");
        when(reviewRepository.findByProductOrderDetailIdAndClientId(anyLong(), anyLong())).thenReturn(new Review());
        assertThrows(ExistReviewException.class, () -> reviewService.writeReview(requestDto, 1L));
    }
    @Test
    void testIsWrited() {
        when(reviewRepository.findByProductOrderDetailIdAndClientId(anyLong(), anyLong())).thenReturn(new Review());
        boolean result = reviewService.isWrited(1L, 1L);
        assertTrue(result);
    }
    @Test
    void testMyReviews() {
        Page<Review> reviewPage = new PageImpl<>(Collections.singletonList(new Review()));
        when(reviewRepository.findByClientId(anyLong(), any(PageRequest.class))).thenReturn(reviewPage);
        Page<ReviewInfoResponseDto> result = reviewService.myReviews(0, 10, 1L);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
    @Test
    void testGetReviewInfo() {
        Review review = new Review();
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(review);
        ReviewInfoResponseDto result = reviewService.getReviewInfo(1L);
        assertNotNull(result);
    }
    @Test
    void testUpdateReview() {
        ReviewUpdateRequestDto updateDto = new ReviewUpdateRequestDto(1L, "Updated content");
        Review existingReview = new Review();
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(existingReview);
        String result = reviewService.updateReview(updateDto);
        assertEquals("Success", result);
        assertEquals("Updated content", existingReview.getReviewContent());
        verify(reviewRepository).save(existingReview);
    }
    @Test
    void testGetAverageReviewScore() {
        when(reviewRepository.getAverageReviewScoreByProductId(anyLong())).thenReturn(4.5);
        Double score = reviewService.getAverageReviewScore(1L);
        assertEquals(4.5, score);
    }
    @Test
    void testProductReviews() {
        Page<Review> reviewPage = new PageImpl<>(Collections.singletonList(new Review()));
        when(reviewRepository.findByProductId(anyLong(), any(PageRequest.class))).thenReturn(reviewPage);
        Page<ReviewInfoResponseDto> result = reviewService.productReviews(1L, 0, 10);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testContainsImage() {
        String htmlWithImage = "<p>Test content</p><img src='test.jpg'/>";
        String htmlWithoutImage = "<p>Test content</p>";

        assertTrue((Boolean) ReflectionTestUtils.invokeMethod(reviewService, "containsImage", htmlWithImage));
        assertFalse((Boolean) ReflectionTestUtils.invokeMethod(reviewService, "containsImage", htmlWithoutImage));
        assertFalse((Boolean) ReflectionTestUtils.invokeMethod(reviewService, "containsImage", ""));
        assertFalse((Boolean) ReflectionTestUtils.invokeMethod(reviewService, "containsImage", (String) null));
    }
}