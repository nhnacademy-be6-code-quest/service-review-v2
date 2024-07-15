package com.nhnacademy.servicereview_v2.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExistReviewExceptionTest {

    @Test
    void testExistReviewExceptionMessage() {
        String expectedMessage = "Review already exists";
        ExistReviewException exception = assertThrows(ExistReviewException.class,
                () -> {
                    throw new ExistReviewException(expectedMessage);
                });

        assertEquals(expectedMessage, exception.getMessage(), "The message should match the expected message.");
    }
}

