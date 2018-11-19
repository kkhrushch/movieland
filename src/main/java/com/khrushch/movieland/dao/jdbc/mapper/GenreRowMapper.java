package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Genre(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
    }
}
