package com.nhnacademy.servicereview_v2.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequestDto {
    private Long reviewId;
    private String reviewContent;
}
