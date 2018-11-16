package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Genre;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GenreRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("вестерн");

        Genre expectedGenre = new Genre(1L, "вестерн");

        RowMapper<Genre> rowMapper = new GenreRowMapper();
        Genre actualGenre = rowMapper.mapRow(mockResultSet, 0);

        assertEquals(expectedGenre, actualGenre);
    }
}