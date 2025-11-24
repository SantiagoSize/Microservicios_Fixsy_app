package com.fixsy.reviewsservice.controller;

import com.fixsy.reviewsservice.dto.ReviewResponse;
import com.fixsy.reviewsservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    void getByIdShouldReturn200() throws Exception {
        ReviewResponse response = ReviewResponse.builder()
                .id(1L)
                .mechanicId(10L)
                .userId(3L)
                .rating(5)
                .comment("Excelente")
                .build();
        Mockito.when(reviewService.getReview(1L)).thenReturn(response);

        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating", is(5)))
                .andExpect(jsonPath("$.mechanicId", is(10)));
    }

    @Test
    void getByMechanicShouldReturnList() throws Exception {
        ReviewResponse r1 = ReviewResponse.builder().id(1L).mechanicId(5L).rating(4).build();
        ReviewResponse r2 = ReviewResponse.builder().id(2L).mechanicId(5L).rating(5).build();
        Mockito.when(reviewService.getReviewsByMechanic(5L)).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/reviews/mechanic/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rating", is(4)));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        mockMvc.perform(delete("/reviews/8"))
                .andExpect(status().isNoContent());
        Mockito.verify(reviewService).deleteReview(8L);
    }
}
