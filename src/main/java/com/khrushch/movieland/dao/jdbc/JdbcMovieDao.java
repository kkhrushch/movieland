package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.khrushch.movieland.dao.jdbc.query.MovieQuery;
import com.khrushch.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcMovieDao implements MovieDao {
    static final RowMapper<Movie> MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll(Map<String,String> soringParams) {
        return jdbcTemplate.query(MovieQuery.getSelectAllMoviesSql(soringParams), MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getRandom() {
        return jdbcTemplate.query(MovieQuery.getSelectRandomMoviesSql(), MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getByGenreId(long genreId, Map<String,String> soringParams){
        return jdbcTemplate.query(MovieQuery.getSelectMoviesByGenreId(soringParams), MOVIE_ROW_MAPPER, genreId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
