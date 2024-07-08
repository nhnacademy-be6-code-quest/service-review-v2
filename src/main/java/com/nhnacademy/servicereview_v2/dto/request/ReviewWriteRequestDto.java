package com.nhnacademy.servicereview_v2.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewWriteRequestDto {
    private Long productId;
    private Long productOrderDetailId;
    private byte reviewScore;
    private String reviewContent;
}
