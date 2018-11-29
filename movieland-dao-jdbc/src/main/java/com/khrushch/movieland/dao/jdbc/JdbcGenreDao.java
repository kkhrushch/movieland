package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.khrushch.movieland.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private static final String SELECT_ALL_GENRES_SQL = "SELECT id, name FROM genre";
    private static final String SELECT_GENRES_BY_MOVIE_ID = "" +
            " SELECT g.id" +
            "      , g.name" +
            "   FROM movie_genre          mg" +
            "        JOIN" +
            "        genre                g" +
            "          ON mg.genre_id     = g.id" +
            "  WHERE mg.movie_id          = ?";
    private static final RowMapper<Genre> GENRE_ROW_MAPPER = new GenreRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(SELECT_ALL_GENRES_SQL, GENRE_ROW_MAPPER);
    }

    @Override
    public List<Genre> getByMovieId(long movieId){
        return jdbcTemplate.query(SELECT_GENRES_BY_MOVIE_ID, GENRE_ROW_MAPPER, movieId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
