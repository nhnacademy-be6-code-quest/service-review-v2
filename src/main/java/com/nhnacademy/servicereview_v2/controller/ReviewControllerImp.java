package com.nhnacademy.servicereview_v2.controller;

import com.nhnacademy.servicereview_v2.dto.request.ReviewWriteRequestDto;
import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import com.nhnacademy.servicereview_v2.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewControllerImp implements ReviewController {
    private final ReviewService reviewService;

    @PostMapping(path = "/api/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageUploadResponseDto.SuccessFile>> uploadImages(
            @RequestPart("files") List<MultipartFile> files
    ) {
        return ResponseEntity.ok(reviewService.uploadImage(files));
    }

    @PostMapping(path = "/api/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponseDto.SuccessFile> uploadImages(
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(reviewService.uploadImage(List.of(file)).getFirst());
    }

    @PostMapping("/api/review")
    public ResponseEntity<String> writeReview(
            @RequestBody ReviewWriteRequestDto reviewWriteRequestDto,
            @RequestHeader("X-User-Id") String clientIdHeader
    ) {
        return ResponseEntity.ok(reviewService.writeReview(reviewWriteRequestDto, Long.valueOf(clientIdHeader)));
    }
}
