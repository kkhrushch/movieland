package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getByMovieId(long movieId);

    void addReview(Review review);
}
