package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        User user = new User(rs.getLong("user_id"), rs.getString("user_nickname"));
        String text = rs.getString("text");

        Review review = new Review();
        review.setId(id);
        review.setUser(user);
        review.setText(text);

        return review;
    }
}
