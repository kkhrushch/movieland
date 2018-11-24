package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Country;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("name")).thenReturn("США");

        Country expectedCountry = new Country(1, "США");

        RowMapper<Country> rowMapper = new CountryRowMapper();
        Country actualCountry = rowMapper.mapRow(mockResultSet, 0);

        assertEquals(expectedCountry, actualCountry);
    }
}