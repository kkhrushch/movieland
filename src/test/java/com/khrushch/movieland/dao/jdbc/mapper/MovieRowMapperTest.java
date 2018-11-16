package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Movie;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getLong("id")).thenReturn(Long.valueOf(1));
        when(rs.getString("name")).thenReturn("Список Шиндлера");
        when(rs.getString("original_name")).thenReturn("Schindler's List");
        when(rs.getInt("year")).thenReturn(1993);
        when(rs.getDouble("rating")).thenReturn(8.7);
        when(rs.getDouble("price")).thenReturn(150.5);
        when(rs.getString("poster_url")).thenReturn("https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg");

        Movie actualMovie = new MovieRowMapper().mapRow(rs, 0);
        Movie expectedMovie = getTestMovie();
        assertEquals(expectedMovie, actualMovie);
    }

    private Movie getTestMovie() {
        Movie movie = new Movie();

        movie.setId(1);
        movie.setRussianName("Список Шиндлера");
        movie.setNativeName("Schindler's List");
        movie.setYearOfRelease(1993);
        movie.setRating(8.7);
        movie.setPrice(150.5);
        movie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg");

        return movie;
    }
}