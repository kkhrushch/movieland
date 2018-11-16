package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.MovieDao;
import com.khrushch.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.khrushch.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    // visibility for tests
    static final String SELECT_ALL_MOVIES_SQL = "SELECT id, name, original_name, year, rating, price, poster_url FROM movie";
    static final String SELECT_MOVIES_BY_GENRE_ID = "" +
            "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, m.poster_url " +
            "  FROM movie          m " +
            "       JOIN " +
            "       movie_genre    mg " +
            "         ON m.id      = mg.movie_id " +
            " WHERE mg.genre_id  = ?";

    static final RowMapper<Movie> MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(SELECT_ALL_MOVIES_SQL, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getByGenreId(long genreId){
        return jdbcTemplate.query(SELECT_MOVIES_BY_GENRE_ID, MOVIE_ROW_MAPPER, genreId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
