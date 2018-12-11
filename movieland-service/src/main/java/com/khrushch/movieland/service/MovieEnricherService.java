package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;

public interface MovieEnricherService {
    void enrich(Movie movie, QueryParam queryParam);
}
