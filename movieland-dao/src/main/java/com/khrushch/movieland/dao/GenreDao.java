package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
    List<Genre> getByMovieId(long movieId);
}
