package com.fixsy.reviewsservice.service;

import com.fixsy.reviewsservice.dto.ReviewCreateRequest;
import com.fixsy.reviewsservice.dto.ReviewResponse;
import com.fixsy.reviewsservice.entity.Review;
import com.fixsy.reviewsservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewResponse createReview(ReviewCreateRequest request) {
        Review entity = new Review();
        entity.setMechanicId(request.getMechanicId());
        entity.setUserId(request.getUserId());
        entity.setRating(request.getRating());
        entity.setComment(request.getComment());
        return toResponse(reviewRepository.save(entity));
    }

    public ReviewResponse getReview(Long id) {
        Review entity = findOrThrow(id);
        return toResponse(entity);
    }

    public List<ReviewResponse> getReviewsByMechanic(Long mechanicId) {
        return reviewRepository.findByMechanicId(mechanicId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada");
        }
        reviewRepository.deleteById(id);
    }

    private Review findOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reseña no encontrada"));
    }

    private ReviewResponse toResponse(Review entity) {
        return ReviewResponse.builder()
                .id(entity.getId())
                .mechanicId(entity.getMechanicId())
                .userId(entity.getUserId())
                .rating(entity.getRating())
                .comment(entity.getComment())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
