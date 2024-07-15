package com.nhnacademy.servicereview_v2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.servicereview_v2.dto.request.ReviewUpdateRequestDto;
import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.dto.response.ReviewInfoResponseDto;
import com.nhnacademy.servicereview_v2.exception.ExistReviewException;
import com.nhnacademy.servicereview_v2.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewControllerImp.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUploadImages() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());
        ImageUploadResponseDto.SuccessFile successFile = new ImageUploadResponseDto.SuccessFile();
        when(reviewService.uploadImage(anyList())).thenReturn(Collections.singletonList(successFile));

        mockMvc.perform(multipart("/api/images").file(file))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(successFile))));
    }

    @Test
    void testUploadSingleImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());
        ImageUploadResponseDto.SuccessFile successFile = new ImageUploadResponseDto.SuccessFile();
        when(reviewService.uploadImage(anyList())).thenReturn(Collections.singletonList(successFile));

        mockMvc.perform(multipart("/api/image").file(file))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(successFile)));
    }

    @Test
    void testWriteReview() throws Exception {
        ReviewWriteRequestDto writeDto = new ReviewWriteRequestDto(1L, 1L, (byte) 5, "Great product!");
        when(reviewService.writeReview(any(ReviewWriteRequestDto.class), anyLong())).thenReturn("Success");

        mockMvc.perform(post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(writeDto))
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testIsWrited() throws Exception {
        when(reviewService.isWrited(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(get("/api/review/1")
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetMyReviews() throws Exception {
        Page<ReviewInfoResponseDto> page = new PageImpl<>(Collections.singletonList(new ReviewInfoResponseDto()));
        when(reviewService.myReviews(anyInt(), anyInt(), anyLong())).thenReturn(page);

        mockMvc.perform(get("/api/reviews/my")
                        .param("page", "0")
                        .param("size", "10")
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    void testGetReviewInfo() throws Exception {
        ReviewInfoResponseDto responseDto = new ReviewInfoResponseDto();
        when(reviewService.getReviewInfo(anyLong())).thenReturn(responseDto);

        mockMvc.perform(get("/api/review")
                        .param("reviewId", "1")
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void testUpdateReview() throws Exception {
        ReviewUpdateRequestDto updateDto = new ReviewUpdateRequestDto(1L, "Updated content");
        when(reviewService.updateReview(any(ReviewUpdateRequestDto.class))).thenReturn("Success");

        mockMvc.perform(put("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto))
                        .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testGetReviewScore() throws Exception {
        when(reviewService.getAverageReviewScore(anyLong())).thenReturn(4.5);

        mockMvc.perform(get("/api/review/score")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }

    @Test
    void testGetReviewProducts() throws Exception {
        Page<ReviewInfoResponseDto> page = new PageImpl<>(Collections.singletonList(new ReviewInfoResponseDto()));
        when(reviewService.productReviews(anyLong(), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/reviews/product")
                        .param("page", "0")
                        .param("size", "10")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(page)));
    }

    @Test
    void testExistReviewException() throws Exception {
        when(reviewService.writeReview(any(ReviewWriteRequestDto.class), anyLong()))
                .thenThrow(new ExistReviewException("Review already exists"));

        ReviewWriteRequestDto writeDto = new ReviewWriteRequestDto(1L, 1L, (byte) 5, "Great product!");

        mockMvc.perform(post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(writeDto))
                        .header("X-User-Id", "1"))
                .andExpect(status().isBadRequest());
    }
}