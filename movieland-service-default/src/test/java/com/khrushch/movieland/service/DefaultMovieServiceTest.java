package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.MovieQueryParam;
import com.khrushch.movieland.model.request.QueryParam;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultMovieServiceTest {

    @Test
    public void testGetAll() {
        MovieDao mockMovieDao = mock(MovieDao.class);
        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        QueryParam queryParam = new MovieQueryParam(new HashMap<>());

        when(mockMovieDao.getAll(queryParam)).thenReturn(getTestMovies());

        List<Movie> actualMovies = movieService.getAll(queryParam);

        verify(mockMovieDao, times(1)).getAll(queryParam);
        assertEquals(getTestMovies(), actualMovies);
    }

    @Test
    public void testGetRandomMovies() {
        MovieDao mockMovieDao = mock(MovieDao.class);
        when(mockMovieDao.getRandom()).thenReturn(getTestMovies());

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        List<Movie> actualMovies = movieService.getRandom();

        assertEquals(getTestMovies(), actualMovies);
        verify(mockMovieDao, times(1)).getRandom();
    }

    @Test
    public void testGetByGenreId() {
        MovieDao mockMovieDao = mock(MovieDao.class);

        QueryParam queryParam = new MovieQueryParam(new HashMap<>());

        when(mockMovieDao.getByGenreId(1, queryParam)).thenReturn(getTestMovies());

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        List<Movie> actualMovies = movieService.getByGenreId(1, queryParam);

        verify(mockMovieDao, times(1)).getByGenreId(1, queryParam);
        assertEquals(getTestMovies(), actualMovies);
    }

    @Test
    public void testGetById() {
        QueryParam mockQueryParam = mock(QueryParam.class);

        Movie movie = getTestMovies().get(0);

        MovieDao mockMovieDao = mock(MovieDao.class);
        when(mockMovieDao.getById(1L)).thenReturn(movie);

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(mockMovieDao);

        MovieEnricherService mockMovieEnricherService = mock(MovieEnricherService.class);
        movieService.setMovieEnricherService(mockMovieEnricherService);

        Movie actualMovie = movieService.getById(1L, mockQueryParam);

        verify(mockMovieDao, times(1)).getById(1L);
        verify(mockMovieEnricherService, times(1)).enrich(movie, mockQueryParam);
        assertEquals(movie, actualMovie);
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

        return Arrays.asList(first, second);
    }

}