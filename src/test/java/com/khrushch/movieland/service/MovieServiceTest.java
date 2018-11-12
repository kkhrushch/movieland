package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Test
    public void testGetAll() {
        MovieDao movieDao = mock(MovieDao.class);
        MovieService movieService = new MovieService();
        movieService.setMovieDao(movieDao);

        List<Movie> movies = new ArrayList<>(Arrays.asList(new Movie(), new Movie()));
        when(movieDao.getAll()).thenReturn(movies);

        List<Movie> resultMovies = movieService.getAll();

        verify(movieDao, times(1)).getAll();
        assertEquals(resultMovies.size(), 2);
    }
}