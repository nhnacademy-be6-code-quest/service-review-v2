package com.nhnacademy.servicereview_v2.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWriteRequestDto {
    private Long productId;
    private Long productOrderDetailId;
    private byte reviewScore;
    private String reviewContent;
}
