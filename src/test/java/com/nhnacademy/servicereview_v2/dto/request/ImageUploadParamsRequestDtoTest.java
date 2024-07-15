package com.nhnacademy.servicereview_v2.dto.request;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImageUploadParamsRequestDtoTest {

    @Test
    void testBuilderAndAccessors() {
        String basePath = "/images/uploads";
        boolean autoRename = true;
        boolean overwrite = false;
        List<String> operationIds = Arrays.asList("op123", "op456");

        ImageUploadParamsRequestDto dto = ImageUploadParamsRequestDto.builder()
                .basepath(basePath)
                .autorename(autoRename)
                .overwrite(overwrite)
                .operationIds(operationIds)
                .build();

        // Test getters
        assertThat(dto.getBasepath()).isEqualTo(basePath);
        assertThat(dto.isAutorename()).isEqualTo(autoRename);
        assertThat(dto.isOverwrite()).isEqualTo(overwrite);
        assertThat(dto.getOperationIds()).containsExactlyElementsOf(operationIds);

        // Test setters
        dto.setBasepath("/new/path");
        dto.setAutorename(false);
        dto.setOverwrite(true);
        List<String> newOperationIds = Arrays.asList("op789");
        dto.setOperationIds(newOperationIds);

        assertThat(dto.getBasepath()).isEqualTo("/new/path");
        assertThat(dto.isAutorename()).isFalse();
        assertThat(dto.isOverwrite()).isTrue();
        assertThat(dto.getOperationIds()).containsExactlyElementsOf(newOperationIds);
    }
}
