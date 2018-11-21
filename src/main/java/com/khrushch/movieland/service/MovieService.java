package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {
    List<Movie> getAll(Map<String,String> requestParams);

    List<Movie> getRandom();

    List<Movie> getByGenreId(long genreId, Map<String,String> requestParams);
}
