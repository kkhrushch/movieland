package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.jdbc.query.MovieStatement;
import com.khrushch.movieland.dao.jdbc.query.QueryBuilder;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.MovieQueryParam;
import com.khrushch.movieland.model.request.QueryParam;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JdbcMovieDaoTest {
    @Test
    public void testGetAll() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

        JdbcMovieDao jdbcMovieDao = new JdbcMovieDao();
        jdbcMovieDao.setJdbcTemplate(mockJdbcTemplate);

        QueryParam queryParam = new MovieQueryParam(new HashMap<>());
        String query = QueryBuilder.build(MovieStatement.SELECT_ALL_MOVIES_SQL, queryParam);

        when(mockJdbcTemplate.query(query, JdbcMovieDao.MOVIE_ROW_MAPPER)).thenReturn(getTestMovies());

        List<Movie> actualMovies = jdbcMovieDao.getAll(queryParam);

        verify(mockJdbcTemplate, times(1)).query(query, JdbcMovieDao.MOVIE_ROW_MAPPER);
        assertEquals(getTestMovies(), actualMovies);
    }

    @Test
    public void testGetByGenreId() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);

        QueryParam queryParam = new MovieQueryParam(new HashMap<>());
        String query = QueryBuilder.build(MovieStatement.SELECT_MOVIES_BY_GENRE_ID, queryParam);

        when(mockJdbcTemplate.query(query, JdbcMovieDao.MOVIE_ROW_MAPPER, 1L)).thenReturn(getTestMovies());

        JdbcMovieDao jdbcMovieDao = new JdbcMovieDao();
        jdbcMovieDao.setJdbcTemplate(mockJdbcTemplate);

        List<Movie> actualMovies = jdbcMovieDao.getByGenreId(1L, queryParam);

        verify(mockJdbcTemplate, times(1)).query(query, JdbcMovieDao.MOVIE_ROW_MAPPER, 1L);
        assertEquals(getTestMovies(), actualMovies);
    }

    @Test
    public void testGetByMovieId() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.queryForObject(MovieStatement.SELECT_MOVIE_BY_ID, JdbcMovieDao.MOVIE_ROW_MAPPER, 1L)).thenReturn(getTestMovies().get(0));

        JdbcMovieDao movieDao = new JdbcMovieDao();
        movieDao.setJdbcTemplate(mockJdbcTemplate);

        Movie actualMovies = movieDao.getById(1L);

        verify(mockJdbcTemplate, times(1)).queryForObject(MovieStatement.SELECT_MOVIE_BY_ID, JdbcMovieDao.MOVIE_ROW_MAPPER, 1L);
        assertEquals(getTestMovies().get(0), actualMovies);
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