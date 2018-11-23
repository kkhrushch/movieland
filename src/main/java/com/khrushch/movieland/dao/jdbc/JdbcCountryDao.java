package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.CountryDao;
import com.khrushch.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.khrushch.movieland.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {
    private static final RowMapper<Country> COUNTRY_ROW_MAPPER = new CountryRowMapper();
    static final String SELECT_COUNTRIES_BY_MOVIE_ID_SQL = "" +
            " SELECT c.id" +
            "      , c.name" +
            "   FROM movie_country       mc" +
            "        JOIN" +
            "        country             c" +
            "          ON mc.country_id  = c.id" +
            "  WHERE movie_id            = ?";

    private JdbcTemplate jdbcTemplate;

    public List<Country> getByMovieId(long movieId) {
        return jdbcTemplate.query(SELECT_COUNTRIES_BY_MOVIE_ID_SQL, COUNTRY_ROW_MAPPER, movieId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
