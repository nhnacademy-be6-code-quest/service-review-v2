package com.nhnacademy.servicereview_v2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"productOrderDetailId", "clientId"})})
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
