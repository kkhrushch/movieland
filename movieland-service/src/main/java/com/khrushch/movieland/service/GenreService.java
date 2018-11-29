package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Genre;
import com.khrushch.movieland.model.Movie;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    void enrich(Movie movie);
}
