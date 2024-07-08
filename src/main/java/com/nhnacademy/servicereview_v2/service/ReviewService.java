package com.nhnacademy.servicereview_v2.service;

import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    List<ImageUploadResponseDto.SuccessFile> uploadImage(List<MultipartFile> files);
    String writeReview(ReviewWriteRequestDto reviewWriteRequestDto, Long clientId);
}
