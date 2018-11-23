package com.khrushch.movieland.service;

import com.khrushch.movieland.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> getByMovieId(long movieId);
}
