package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();

    List<Movie> getRandom();

    List<Movie> getByGenreId(long genreId);
}
