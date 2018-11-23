package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.dao.ReviewDao;
import com.khrushch.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.khrushch.movieland.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private static final String SELECT_REVIEWS_BY_MOVIE_ID = "" +
            " SELECT mr.id" +
            "      , au.id                user_id" +
            "      , au.nickname          user_nickname" +
            "      , text" +
            "   FROM movie_review         mr" +
            "        JOIN" +
            "        app_user             au" +
            "          ON mr.app_user_id  = au.id" +
            "  WHERE mr.movie_id          = ?";
    private static final RowMapper<Review> REVIEW_ROW_MAPPER = new ReviewRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> getByMovieId(long movieId) {
        return jdbcTemplate.query(SELECT_REVIEWS_BY_MOVIE_ID, REVIEW_ROW_MAPPER, movieId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
