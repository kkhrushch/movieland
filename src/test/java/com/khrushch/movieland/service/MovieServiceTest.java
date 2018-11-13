package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Test
    public void testGetAll() {
        MovieDao movieDao = mock(MovieDao.class);
        MovieService movieService = new MovieService();
        movieService.setMovieDao(movieDao);

        when(movieDao.getAll()).thenReturn(getTestMovies());

        List<Movie> actualMovies = movieService.getAll();

        verify(movieDao, times(1)).getAll();
        assertEquals(getTestMovies(), actualMovies);
    }

    @Test
    public void getRandomMovies() {
        List<Movie> mockMovies = Stream.generate(Movie::new)
                .limit(20)
                .collect(Collectors.toList());

        MovieDao movieDao = mock(MovieDao.class);
        when(movieDao.getAll()).thenReturn(mockMovies);

        MovieService movieService = new MovieService();
        movieService.setMovieDao(movieDao);

        List<Movie> actualMovies = movieService.getRandomMovies();

        assertEquals(3, actualMovies.size());
        verify(movieDao, times(1)).getAll();
    }

    private List<Movie> getTestMovies() {
        Movie first = new Movie();
        first.setId(1);
        first.setRussianName("Список Шиндлера");
        first.setNativeName("Schindler's List");
        first.setYearOfRelease(1993);
        first.setRating(8.7);
        first.setPrice(150.5);
        first.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg");

        Movie second = new Movie();
        second.setId(2);
        second.setRussianName("Унесённые призраками");
        second.setNativeName("Sen to Chihiro no kamikakushi");
        second.setYearOfRelease(2001);
        second.setRating(8.6);
        second.setPrice(145.9);
        second.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BOGJjNzZmMmUtMjljNC00ZjU5LWJiODQtZmEzZTU0MjBlNzgxL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1._SY209_CR0,0,140,209_.jpg");

        return new ArrayList<>(Arrays.asList(first, second));
    }

}