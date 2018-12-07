package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReviewControllerTest {
    @Mock
    private ReviewService mockReviewService;

    @InjectMocks
    ReviewController reviewController;

    @Test
    public void testAddMovie() {
        Review review = new Review();
        review.setMovieId(1);
        review.setText("aText");
        review.setUser(new User());

        reviewController.addMovie(review);

        verify(mockReviewService, times(1)).addReview(review);

    }
}