package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.Country;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

public class JdbcCountryDaoTest {

    @Test
    public void testGetByMovieId() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(any(String.class), Matchers.<RowMapper<Country>>any(), anyLong())).thenReturn(getTestCountries());

        JdbcCountryDao jdbcCountryDao = new JdbcCountryDao();
        jdbcCountryDao.setJdbcTemplate(mockJdbcTemplate);

        List<Country> actualCountries = jdbcCountryDao.getByMovieId(0L);

        verify(mockJdbcTemplate, times(1)).query(any(String.class), Matchers.<RowMapper<Country>>any(), anyLong());
        assertEquals(getTestCountries(), actualCountries);
    }

    private List<Country> getTestCountries() {
        return Arrays.asList(
                new Country(1, "США"),
                new Country(2, "Франция")
        );
    }
}