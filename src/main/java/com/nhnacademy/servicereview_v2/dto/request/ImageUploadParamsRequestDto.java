package com.nhnacademy.servicereview_v2.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ImageUploadParamsRequestDto {
    private String basepath;
    private boolean autorename;
    private boolean overwrite;
    private List<String> operationIds;
}