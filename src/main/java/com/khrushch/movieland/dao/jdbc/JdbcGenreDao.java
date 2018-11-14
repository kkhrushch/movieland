package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.GenreDao;
import com.khrushch.movieland.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private static final String SELECT_ALL_GENRES_SQL = "SELECT id, name FROM genre";

    private JdbcTemplate jdbcTemplate;

    public List<Genre> getAll() {
        return jdbcTemplate.query(SELECT_ALL_GENRES_SQL, (resultSet, i) -> new Genre(resultSet.getLong("id"), resultSet.getString("name")));
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
