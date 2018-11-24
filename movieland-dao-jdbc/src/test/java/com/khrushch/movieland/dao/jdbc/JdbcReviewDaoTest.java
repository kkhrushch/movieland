package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import org.junit.Test;
import org.mockito.Matchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcReviewDaoTest {

    @Test
    public void testGetByMovieId() {
        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.query(anyString(), Matchers.<RowMapper<Review>>any(), anyDouble())).thenReturn(getTestReviews());

        JdbcReviewDao jdbcReviewDao = new JdbcReviewDao();
        jdbcReviewDao.setJdbcTemplate(mockJdbcTemplate);

        List<Review> actualReviews = jdbcReviewDao.getByMovieId(1L);

        assertEquals(getTestReviews(), actualReviews);
    }

    private List<Review> getTestReviews() {
        Review first = new Review();
        first.setId(1L);
        first.setUser(new User(1L, "a nickname"));
        first.setText("a review");

        Review second = new Review();
        second.setId(2L);
        second.setUser(new User(2L, "another nickname"));
        second.setText("another review");

        return Arrays.asList(first, second);
    }

}