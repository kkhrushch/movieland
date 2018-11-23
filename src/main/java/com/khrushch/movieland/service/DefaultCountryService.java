package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.CountryDao;
import com.khrushch.movieland.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    @Override

    public List<Country> getByMovieId(long movieId) {
        return countryDao.getByMovieId(movieId);
    }

    @Autowired
    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}
