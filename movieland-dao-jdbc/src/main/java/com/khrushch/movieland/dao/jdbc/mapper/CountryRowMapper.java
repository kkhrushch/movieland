package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Country(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
