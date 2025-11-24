package com.fixsy.reviewsservice.service;

import com.fixsy.reviewsservice.entity.Review;
import com.fixsy.reviewsservice.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void deleteShouldFailWhenReviewNotFound() {
        when(reviewRepository.existsById(9L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> reviewService.deleteReview(9L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void getReviewShouldReturnMappedResponse() {
        Review entity = new Review();
        entity.setId(3L);
        entity.setMechanicId(7L);
        entity.setUserId(2L);
        entity.setRating(5);
        entity.setComment("Excelente");

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(entity));

        var response = reviewService.getReview(3L);

        assertEquals(3L, response.getId());
        assertEquals(7L, response.getMechanicId());
        assertEquals(2L, response.getUserId());
        assertEquals(5, response.getRating());
        assertEquals("Excelente", response.getComment());
        verify(reviewRepository).findById(3L);
    }
}
