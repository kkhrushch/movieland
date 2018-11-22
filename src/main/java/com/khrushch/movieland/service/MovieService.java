package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;

import java.util.List;

public interface MovieService {
    List<Movie> getAll(QueryParam queryParam);

    List<Movie> getRandom();

    List<Movie> getByGenreId(long genreId, QueryParam queryParam);
}
