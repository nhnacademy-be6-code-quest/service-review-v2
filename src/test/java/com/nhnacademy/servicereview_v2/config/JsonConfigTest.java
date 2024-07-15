package com.nhnacademy.servicereview_v2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonConfigTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDeserializeLocalDateTime() throws Exception {
        String json = "\"2023-01-01T12:00:00\"";
        LocalDateTime dateTime = objectMapper.readValue(json, LocalDateTime.class);

        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), dateTime);
    }

    @Test
    void testSerializeEmptyObject() {
        class EmptyClass {}
        EmptyClass emptyObject = new EmptyClass();

        // 빈 객체 직렬화 시 예외가 발생하지 않아야 함
        assertDoesNotThrow(() -> objectMapper.writeValueAsString(emptyObject));
    }
}