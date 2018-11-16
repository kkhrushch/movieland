package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.Genre;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JdbcGenreDaoTest {

    @Test
    public void getAll() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(any(String.class), Matchers.<RowMapper<Genre>>any())).thenReturn(getTestGenres());

        JdbcGenreDao jdbcGenreDao = new JdbcGenreDao();
        jdbcGenreDao.setJdbcTemplate(mockJdbcTemplate);

        List<Genre> actualGenres = jdbcGenreDao.getAll();

        verify(mockJdbcTemplate, times(1)).query(any(String.class), Matchers.<RowMapper<Genre>>any());
        assertEquals(getTestGenres(), actualGenres);
    }

    private List<Genre> getTestGenres() {
        return new ArrayList<>(Arrays.asList(
                new Genre(1, "вестерн"),
                new Genre(2, "ужасы")
        ));
    }

}