package com.nhnacademy.servicereview_v2.controller;

import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewController {
    @PostMapping(path = "/api/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<ImageUploadResponseDto.SuccessFile>> uploadImages(@RequestPart("files") List<MultipartFile> files);

    @PostMapping(path = "/api/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ImageUploadResponseDto.SuccessFile> uploadImages(@RequestPart("file") MultipartFile file);

    @PostMapping("/api/review")
    ResponseEntity<String> writeReview(
            @RequestBody ReviewWriteRequestDto reviewWriteRequestDto,
            @RequestHeader("X-User-Id") String userIdHeader
    );
}
