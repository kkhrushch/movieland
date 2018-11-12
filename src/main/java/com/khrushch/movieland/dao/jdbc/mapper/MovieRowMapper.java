package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Movie;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getLong("id"));
        movie.setRussianName(rs.getString("name"));
        movie.setNativeName(rs.getString("original_name"));
        movie.setYearOfRelease(rs.getInt("year"));
        movie.setRating(rs.getDouble("rating"));
        movie.setPrice(rs.getDouble("price"));
        movie.setPicturePath(rs.getString("poster_url"));

        return movie;
    }
}
