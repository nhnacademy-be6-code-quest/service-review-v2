package com.nhnacademy.servicereview_v2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
