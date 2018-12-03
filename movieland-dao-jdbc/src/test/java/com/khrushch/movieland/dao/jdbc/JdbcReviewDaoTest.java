package com.khrushch.movieland.dao.jdbc;

import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JdbcReviewDaoTest {
    @Mock
    private JdbcTemplate mockJdbcTemplate;

    @InjectMocks
    private JdbcReviewDao jdbcReviewDao;

    @Test
    public void testGetByMovieId() {
        when(mockJdbcTemplate.query(anyString(), Matchers.<RowMapper<Review>>any(), anyDouble())).thenReturn(getTestReviews());

        List<Review> actualReviews = jdbcReviewDao.getByMovieId(1L);

        assertEquals(getTestReviews(), actualReviews);
    }

    @Test
    public void testAddReview() {
        Review review = new Review();
        review.setMovieId(1);
        review.setUser(new User(1, "aNickname"));
        review.setText("aText");
        jdbcReviewDao.addReview(review);

        verify(mockJdbcTemplate, times(1)).update(anyString(), anyLong(), anyLong(), anyString());
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