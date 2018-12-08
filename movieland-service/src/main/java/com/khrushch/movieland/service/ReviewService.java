package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.Review;

public interface ReviewService {
    void addReview(Review review);

    void enrich(Movie movie);
}
