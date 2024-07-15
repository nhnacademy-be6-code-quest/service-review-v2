package com.nhnacademy.servicereview_v2.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ImageUploadResponseDtoTest {

    @Test
    void testImageUploadResponseDto() {
        ImageUploadResponseDto.Header header = new ImageUploadResponseDto.Header("Success", 200, true);
        ImageUploadResponseDto.ErrorFile.ErrorInfo errorInfo = new ImageUploadResponseDto.ErrorFile.ErrorInfo(404, "Not Found");
        ImageUploadResponseDto.ErrorFile errorFile = new ImageUploadResponseDto.ErrorFile("path/error.jpg", 1024, errorInfo);
        ImageUploadResponseDto.SuccessFile successFile = getSuccessFile();

        ImageUploadResponseDto dto = new ImageUploadResponseDto(header, Collections.singletonList(errorFile), Collections.singletonList(successFile));

        assertThat(dto.getHeader()).isEqualTo(header);
        assertThat(dto.getErrors()).containsExactly(errorFile);
        assertThat(dto.getSuccesses()).containsExactly(successFile);
    }

    private ImageUploadResponseDto.SuccessFile getSuccessFile() {
        ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate coordinate = new ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate(37.7749, -122.4194);
        ImageUploadResponseDto.SuccessFile.ImageProperty imageProperty = new ImageUploadResponseDto.SuccessFile.ImageProperty(800, 600, "2023-07-08T12:00:00Z", coordinate);
        ImageUploadResponseDto.SuccessFile.Queue queue = new ImageUploadResponseDto.SuccessFile.Queue("q1", "upload", "pending", 1, "2023-07-08T12:10:00Z", "op123", "http://example.com", "image.jpg", "path/to/image.jpg");
        return new ImageUploadResponseDto.SuccessFile(false, "id123", "http://example.com/image.jpg", "image.jpg", "path/to/image.jpg", 2048, "user123", "2023-07-08T12:05:00Z", "op123", imageProperty, Collections.singletonList(queue));
    }

    @Test
    void testNestedClasses() {
        ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate coordinate = new ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate(37.7749, -122.4194);
        assertThat(coordinate.getLat()).isEqualTo(37.7749);
        assertThat(coordinate.getLng()).isEqualTo(-122.4194);

        ImageUploadResponseDto.SuccessFile.ImageProperty imageProperty = new ImageUploadResponseDto.SuccessFile.ImageProperty(800, 600, "2023-07-08T12:00:00Z", coordinate);
        assertThat(imageProperty.getWidth()).isEqualTo(800);
        assertThat(imageProperty.getHeight()).isEqualTo(600);
        assertThat(imageProperty.getCoordinate()).isEqualTo(coordinate);

        ImageUploadResponseDto.SuccessFile.Queue queue = new ImageUploadResponseDto.SuccessFile.Queue("q1", "upload", "pending", 1, "2023-07-08T12:10:00Z", "op123", "http://example.com", "image.jpg", "path/to/image.jpg");
        assertThat(queue.getQueueId()).isEqualTo("q1");
        assertThat(queue.getQueueType()).isEqualTo("upload");
        assertThat(queue.getStatus()).isEqualTo("pending");
        assertThat(queue.getTryCount()).isEqualTo(1);
    }
}
