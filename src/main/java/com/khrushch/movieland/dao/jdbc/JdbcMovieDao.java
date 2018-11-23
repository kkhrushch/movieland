package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.khrushch.movieland.dao.jdbc.query.MovieStatement;
import com.khrushch.movieland.dao.jdbc.query.QueryBuilder;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.request.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    static final RowMapper<Movie> MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll(QueryParam queryParam) {
        String query = QueryBuilder.build(MovieStatement.SELECT_ALL_MOVIES_SQL, queryParam);
        return jdbcTemplate.query(query, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getRandom() {
        return jdbcTemplate.query(MovieStatement.SELECT_RANDOM_MOVIES_SQL, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getByGenreId(long genreId, QueryParam queryParam) {
        String query = QueryBuilder.build(MovieStatement.SELECT_MOVIES_BY_GENRE_ID, queryParam);
        return jdbcTemplate.query(query, MOVIE_ROW_MAPPER, genreId);
    }

    @Override
    public Movie getById(long id){
        return jdbcTemplate.queryForObject(MovieStatement.SELECT_MOVIE_BY_ID, MOVIE_ROW_MAPPER, id);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
