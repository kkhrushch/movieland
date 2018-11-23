package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.CountryDao;
import com.khrushch.movieland.model.Country;
import com.khrushch.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    @Override

    public void enrich(Movie movie) {
        List<Country> countries = countryDao.getByMovieId(movie.getId());
        movie.setCountries(countries);
    }

    @Autowired
    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}
