package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll();

    List<Movie> getByGenreId(long genreId);
}
