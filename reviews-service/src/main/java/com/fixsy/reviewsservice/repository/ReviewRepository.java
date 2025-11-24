package com.fixsy.reviewsservice.repository;

import com.fixsy.reviewsservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMechanicId(Long mechanicId);
    List<Review> findByUserId(Long userId);
}
