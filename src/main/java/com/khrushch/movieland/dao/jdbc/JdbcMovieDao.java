package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private static final String SELECT_ALL_MOVIES_SQL = "SELECT id, name, original_name, year, rating, price, poster_url FROM movie";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Movie> movieRowMapper;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(SELECT_ALL_MOVIES_SQL, movieRowMapper);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setMovieRowMapper(RowMapper<Movie> movieRowMapper) {
        this.movieRowMapper = movieRowMapper;
    }
}
