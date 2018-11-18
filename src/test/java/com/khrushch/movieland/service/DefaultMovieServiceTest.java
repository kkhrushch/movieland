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

public class DefaultMovieServiceTest {

    @Test
    public void testGetAll() {
        MovieDao mockMovieDao = mock(MovieDao.class);
        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        when(mockMovieDao.getAll()).thenReturn(getTestMovies());

        List<Movie> actualMovies = movieService.getAll();

        verify(mockMovieDao, times(1)).getAll();
        assertEquals(getTestMovies(), actualMovies);
    }

    @Test
    public void testGetRandomMovies() {
        List<Movie> mockMovies = Stream.generate(Movie::new)
                .limit(20)
                .collect(Collectors.toList());

        MovieDao mockMovieDao = mock(MovieDao.class);
        when(mockMovieDao.getAll()).thenReturn(mockMovies);

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        List<Movie> actualMovies = movieService.getRandom();

        assertEquals(3, actualMovies.size());
        verify(mockMovieDao, times(1)).getAll();
    }

    @Test
    public void testGetByGenreId(){
        MovieDao mockMovieDao = mock(MovieDao.class);
        when(mockMovieDao.getByGenreId(1)).thenReturn(getTestMovies());

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        List<Movie> actualMovies = movieService.getByGenreId(1);

        verify(mockMovieDao, times(1)).getByGenreId(1);
        assertEquals(getTestMovies(), actualMovies);
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