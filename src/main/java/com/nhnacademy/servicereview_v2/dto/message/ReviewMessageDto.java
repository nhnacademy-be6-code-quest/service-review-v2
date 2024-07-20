package com.nhnacademy.servicereview_v2.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewMessageDto {
    private Long clientId;
    private Boolean hasImage;
}
