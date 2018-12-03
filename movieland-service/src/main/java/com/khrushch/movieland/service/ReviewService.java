package com.khrushch.movieland.service;

import com.khrushch.movieland.dto.ReviewDto;
import com.khrushch.movieland.model.Movie;

public interface ReviewService {
    void addReview(ReviewDto reviewDto);

    void enrich(Movie movie);
}
