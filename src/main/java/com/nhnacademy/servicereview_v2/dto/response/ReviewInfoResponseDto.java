package com.nhnacademy.servicereview_v2.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoResponseDto {
    private Long reviewId;
    private byte reviewScore;
    private String reviewContent;
    private LocalDateTime reviewRegisterDate;
    private LocalDateTime reviewLastModifyDate;
    private Long clientId;
    private Long productOrderDetailId;
    private Long productId;
    private Boolean isDeleted;
}
