package com.khrushch.movieland.dao.jdbc.mapper;

import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import org.junit.Test;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getLong("user_id")).thenReturn(1L);
        when(mockResultSet.getString("user_nickname")).thenReturn("a nickname");
        when(mockResultSet.getString("text")).thenReturn("a review");

        RowMapper<Review> rowMapper = new ReviewRowMapper();
        Review actualReview = rowMapper.mapRow(mockResultSet, 0);

        assertEquals(getTestReview(), actualReview);
    }

    private Review getTestReview() {
        Review review = new Review();
        review.setId(1L);
        review.setUser(new User(1L, "a nickname"));
        review.setText("a review");
        return review;
    }
}