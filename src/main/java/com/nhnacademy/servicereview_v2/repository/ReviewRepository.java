package com.nhnacademy.servicereview_v2.repository;

import com.nhnacademy.servicereview_v2.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
