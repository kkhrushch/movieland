package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.service.MovieService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MovieControllerTest {

    @Test
    public void getAll() {
        MovieService mockMovieService = mock(MovieService.class);
        when(mockMovieService.getAll()).thenReturn(getTestMovies());

        MovieController movieController = new MovieController();
        movieController.setMovieService(mockMovieService);

        List<Movie> actualMovies = mockMovieService.getAll();

        assertEquals(getTestMovies(), actualMovies);
        verify(mockMovieService, times(1)).getAll();
    }

    @Test
    public void getRandomMovies() {
        // Verify that controller returns result from service "as is"

        MovieService mockMovieService = mock(MovieService.class);
        when(mockMovieService.getRandomMovies()).thenReturn(getTestMovies());

        MovieController movieController = new MovieController();
        movieController.setMovieService(mockMovieService);

        List<Movie> actualMovies = mockMovieService.getRandomMovies();

        assertEquals(getTestMovies(), actualMovies);
        verify(mockMovieService, times(1)).getRandomMovies();
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