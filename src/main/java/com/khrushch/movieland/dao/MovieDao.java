package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.Movie;

import java.util.List;
import java.util.Map;

public interface MovieDao {
    List<Movie> getAll(Map<String, String> requestParams);

    List<Movie> getRandom();

    List<Movie> getByGenreId(long genreId, Map<String, String> requestParams);
}
