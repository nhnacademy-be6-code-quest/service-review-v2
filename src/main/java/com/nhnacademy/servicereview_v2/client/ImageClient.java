package com.nhnacademy.servicereview_v2.client;

import com.nhnacademy.servicereview_v2.dto.response.ImageUploadResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "NhnImageManager", url = "https://api-image.nhncloudservice.com/image/v2.0")
public interface ImageClient {
    @PostMapping(value = "/appkeys/${image.manager.app.key}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ImageUploadResponseDto> registerImages(
            @RequestHeader("Authorization") String secretKey,
            @RequestPart("params") String params,
            @RequestPart("files") List<MultipartFile> files
    );
}
