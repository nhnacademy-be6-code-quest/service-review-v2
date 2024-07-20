package com.nhnacademy.servicereview_v2.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImageUploadFailExceptionTest {

    @Test
    void testExceptionMessageIsSet() {
        String errorMessage = "Image upload failed due to network error";
        ImageUploadFailException exception = new ImageUploadFailException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithNullMessage() {
        ImageUploadFailException exception = new ImageUploadFailException(null);

        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        String emptyMessage = "";
        ImageUploadFailException exception = new ImageUploadFailException(emptyMessage);

        assertEquals(emptyMessage, exception.getMessage());
    }
}