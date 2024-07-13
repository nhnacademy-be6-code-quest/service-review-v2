package com.nhnacademy.servicereview_v2.repository;

import com.nhnacademy.servicereview_v2.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByReviewId(Long reviewId);
    Review findByReviewIdAndClientId(Long reviewId, Long userId);
    Review findByProductOrderDetailIdAndClientId(Long productOrderDetailId, Long clientId);
    Page<Review> findByClientId(Long clientId, Pageable pageable);
    Page<Review> findByProductId(Long productId, Pageable pageable);
    @Query("SELECT AVG(r.reviewScore) FROM Review r WHERE r.productId = :productId AND r.isDeleted = false")
    Double getAverageReviewScoreByProductId(@Param("productId") Long productId);
}
