package com.khrushch.movieland.dao;

import com.khrushch.movieland.model.Country;

import java.util.List;

public interface CountryDao {
    List<Country> getByMovieId(long movieId);
}
