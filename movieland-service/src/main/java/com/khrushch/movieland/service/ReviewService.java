package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;

public interface ReviewService {
    void enrich(Movie movie);
}
