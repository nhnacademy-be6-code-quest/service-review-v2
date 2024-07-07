package com.nhnacademy.servicereview_v2.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewImageId;
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;
    private String photoUrl;
}
