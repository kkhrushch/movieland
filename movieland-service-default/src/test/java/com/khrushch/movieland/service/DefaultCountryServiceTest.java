package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.CountryDao;
import com.khrushch.movieland.model.Country;
import com.khrushch.movieland.model.Movie;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultCountryServiceTest {

    @Test
    public void testGetByMovieId() {
        CountryDao mockCountryDao = mock(CountryDao.class);
        when(mockCountryDao.getByMovieId(1L)).thenReturn(getTestCountries());

        DefaultCountryService countryService = new DefaultCountryService();
        countryService.setCountryDao(mockCountryDao);

        Movie movie = new Movie();
        movie.setId(1L);
        countryService.enrich(movie);

        verify(mockCountryDao, times(1)).getByMovieId(1L);
        assertEquals(getTestCountries(), movie.getCountries());

    }

    private List<Country> getTestCountries() {
        return Arrays.asList(
                new Country(1, "США"),
                new Country(2, "Франция")
        );
    }
}