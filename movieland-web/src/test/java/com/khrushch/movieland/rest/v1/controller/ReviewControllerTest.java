package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.dto.ReviewDto;
import com.khrushch.movieland.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
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
        String uuid = "uuid-12345";
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(1);
        reviewDto.setText("aText");

        ReviewDto expectedReviewDto = new ReviewDto();
        expectedReviewDto.setMovieId(reviewDto.getMovieId());
        expectedReviewDto.setText(reviewDto.getText());
        expectedReviewDto.setUuid(uuid);

        reviewController.addMovie(reviewDto, uuid);

        verify(mockReviewService, times(1)).addReview(expectedReviewDto);

    }
}