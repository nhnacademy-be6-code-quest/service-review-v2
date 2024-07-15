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

        // Test setters
        ImageUploadResponseDto.Header newHeader = new ImageUploadResponseDto.Header("Failure", 500, false);
        dto.setHeader(newHeader);
        assertThat(dto.getHeader()).isEqualTo(newHeader);

        ImageUploadResponseDto.ErrorFile newErrorFile = new ImageUploadResponseDto.ErrorFile("new/error.jpg", 2048, new ImageUploadResponseDto.ErrorFile.ErrorInfo(500, "Server Error"));
        dto.setErrors(Collections.singletonList(newErrorFile));
        assertThat(dto.getErrors()).containsExactly(newErrorFile);

        ImageUploadResponseDto.SuccessFile newSuccessFile = getSuccessFile();
        newSuccessFile.setId("newId");
        dto.setSuccesses(Collections.singletonList(newSuccessFile));
        assertThat(dto.getSuccesses()).containsExactly(newSuccessFile);

        // Test toString
        assertThat(dto.toString()).contains("header", "errors", "successes");
    }

    private ImageUploadResponseDto.SuccessFile getSuccessFile() {
        ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate coordinate = new ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate(37.7749, -122.4194);
        ImageUploadResponseDto.SuccessFile.ImageProperty imageProperty = new ImageUploadResponseDto.SuccessFile.ImageProperty(800, 600, "2023-07-08T12:00:00Z", coordinate);
        ImageUploadResponseDto.SuccessFile.Queue queue = new ImageUploadResponseDto.SuccessFile.Queue("q1", "upload", "pending", 1, "2023-07-08T12:10:00Z", "op123", "http://example.com", "image.jpg", "path/to/image.jpg");
        return new ImageUploadResponseDto.SuccessFile(false, "id123", "http://example.com/image.jpg", "image.jpg", "path/to/image.jpg", 2048, "user123", "2023-07-08T12:05:00Z", "op123", imageProperty, Collections.singletonList(queue));
    }

    @Test
    void testHeader() {
        ImageUploadResponseDto.Header header = new ImageUploadResponseDto.Header();
        header.setResultMessage("Success");
        header.setResultCode(200);
        header.setSuccessful(true);
        assertThat(header.getResultMessage()).isEqualTo("Success");
        assertThat(header.getResultCode()).isEqualTo(200);
        assertThat(header.isSuccessful()).isTrue();
        assertThat(header.toString()).contains("resultMessage", "resultCode", "isSuccessful");
    }

    @Test
    void testErrorInfoAndErrorFile() {
        ImageUploadResponseDto.ErrorFile.ErrorInfo errorInfo = new ImageUploadResponseDto.ErrorFile.ErrorInfo();
        errorInfo.setResultCode(404);
        errorInfo.setResultMessage("Not Found");
        assertThat(errorInfo.getResultCode()).isEqualTo(404);
        assertThat(errorInfo.getResultMessage()).isEqualTo("Not Found");
        assertThat(errorInfo.toString()).contains("resultCode", "resultMessage");

        ImageUploadResponseDto.ErrorFile errorFile = new ImageUploadResponseDto.ErrorFile();
        errorFile.setPath("path/error.jpg");
        errorFile.setBytes(1024);
        errorFile.setError(errorInfo);
        assertThat(errorFile.getPath()).isEqualTo("path/error.jpg");
        assertThat(errorFile.getBytes()).isEqualTo(1024);
        assertThat(errorFile.getError()).isEqualTo(errorInfo);
        assertThat(errorFile.toString()).contains("path", "bytes", "error");
    }

    @Test
    void testSuccessFileCoordinate() {
        ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate coordinate = new ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate();
        coordinate.setLat(37.7749);
        coordinate.setLng(-122.4194);
        assertThat(coordinate.getLat()).isEqualTo(37.7749);
        assertThat(coordinate.getLng()).isEqualTo(-122.4194);
        assertThat(coordinate.toString()).contains("lat", "lng");
    }

    @Test
    void testSuccessFileImageProperty() {
        ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate coordinate = new ImageUploadResponseDto.SuccessFile.ImageProperty.Coordinate(37.7749, -122.4194);
        ImageUploadResponseDto.SuccessFile.ImageProperty imageProperty = new ImageUploadResponseDto.SuccessFile.ImageProperty();
        imageProperty.setWidth(800);
        imageProperty.setHeight(600);
        imageProperty.setCreatedAt("2023-07-08T12:00:00Z");
        imageProperty.setCoordinate(coordinate);
        assertThat(imageProperty.getWidth()).isEqualTo(800);
        assertThat(imageProperty.getHeight()).isEqualTo(600);
        assertThat(imageProperty.getCreatedAt()).isEqualTo("2023-07-08T12:00:00Z");
        assertThat(imageProperty.getCoordinate()).isEqualTo(coordinate);
        assertThat(imageProperty.toString()).contains("width", "height", "createdAt", "coordinate");
    }

    @Test
    void testSuccessFileQueue() {
        ImageUploadResponseDto.SuccessFile.Queue queue = new ImageUploadResponseDto.SuccessFile.Queue();
        queue.setQueueId("q1");
        queue.setQueueType("upload");
        queue.setStatus("pending");
        queue.setTryCount(1);
        queue.setQueuedAt("2023-07-08T12:10:00Z");
        queue.setOperationId("op123");
        queue.setUrl("http://example.com");
        queue.setName("image.jpg");
        queue.setPath("path/to/image.jpg");
        assertThat(queue.getQueueId()).isEqualTo("q1");
        assertThat(queue.getQueueType()).isEqualTo("upload");
        assertThat(queue.getStatus()).isEqualTo("pending");
        assertThat(queue.getTryCount()).isEqualTo(1);
        assertThat(queue.getQueuedAt()).isEqualTo("2023-07-08T12:10:00Z");
        assertThat(queue.getOperationId()).isEqualTo("op123");
        assertThat(queue.getUrl()).isEqualTo("http://example.com");
        assertThat(queue.getName()).isEqualTo("image.jpg");
        assertThat(queue.getPath()).isEqualTo("path/to/image.jpg");
        assertThat(queue.toString()).contains("queueId", "queueType", "status", "tryCount", "queuedAt", "operationId", "url", "name", "path");
    }

    @Test
    void testSuccessFile() {
        ImageUploadResponseDto.SuccessFile.ImageProperty imageProperty = new ImageUploadResponseDto.SuccessFile.ImageProperty();
        ImageUploadResponseDto.SuccessFile.Queue queue = new ImageUploadResponseDto.SuccessFile.Queue();

        ImageUploadResponseDto.SuccessFile successFile = new ImageUploadResponseDto.SuccessFile();
        successFile.setFolder(false);
        successFile.setId("id123");
        successFile.setUrl("http://example.com/image.jpg");
        successFile.setName("image.jpg");
        successFile.setPath("path/to/image.jpg");
        successFile.setBytes(2048);
        successFile.setCreatedBy("user123");
        successFile.setUpdatedAt("2023-07-08T12:05:00Z");
        successFile.setOperationId("op123");
        successFile.setImageProperty(imageProperty);
        successFile.setQueues(Collections.singletonList(queue));

        assertThat(successFile.isFolder()).isFalse();
        assertThat(successFile.getId()).isEqualTo("id123");
        assertThat(successFile.getUrl()).isEqualTo("http://example.com/image.jpg");
        assertThat(successFile.getName()).isEqualTo("image.jpg");
        assertThat(successFile.getPath()).isEqualTo("path/to/image.jpg");
        assertThat(successFile.getBytes()).isEqualTo(2048);
        assertThat(successFile.getCreatedBy()).isEqualTo("user123");
        assertThat(successFile.getUpdatedAt()).isEqualTo("2023-07-08T12:05:00Z");
        assertThat(successFile.getOperationId()).isEqualTo("op123");
        assertThat(successFile.getImageProperty()).isEqualTo(imageProperty);
        assertThat(successFile.getQueues()).containsExactly(queue);
        assertThat(successFile.toString()).contains("isFolder", "id", "url", "name", "path", "bytes", "createdBy", "updatedAt", "operationId", "imageProperty", "queues");
    }
}