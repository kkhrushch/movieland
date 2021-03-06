package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.Genre;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JdbcGenreDaoTest {

    @Test
    public void testGetAll() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(any(String.class), Matchers.<RowMapper<Genre>>any())).thenReturn(getTestGenres());

        JdbcGenreDao jdbcGenreDao = new JdbcGenreDao();
        jdbcGenreDao.setJdbcTemplate(mockJdbcTemplate);

        List<Genre> actualGenres = jdbcGenreDao.getAll();

        verify(mockJdbcTemplate, times(1)).query(any(String.class), Matchers.<RowMapper<Genre>>any());
        assertEquals(getTestGenres(), actualGenres);
    }

    @Test
    public void testGetByMovieId() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(any(String.class), Matchers.<RowMapper<Genre>>any(), anyDouble())).thenReturn(getTestGenres());

        JdbcGenreDao jdbcGenreDao = new JdbcGenreDao();
        jdbcGenreDao.setJdbcTemplate(mockJdbcTemplate);

        List<Genre> actualGenres = jdbcGenreDao.getByMovieId(0L);

        verify(mockJdbcTemplate, times(1)).query(any(String.class), Matchers.<RowMapper<Genre>>any(), anyDouble());
        assertEquals(getTestGenres(), actualGenres);
    }

    private List<Genre> getTestGenres() {
        return Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        );
    }

}