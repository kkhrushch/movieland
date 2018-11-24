package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.ReviewDao;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultReviewServiceTest {

    @Test
    public void enrich() {
        ReviewDao mockReviewDao = mock(ReviewDao.class);
        when(mockReviewDao.getByMovieId(1L)).thenReturn(getTestReviews());

        DefaultReviewService defaultReviewService = new DefaultReviewService();
        defaultReviewService.setReviewDao(mockReviewDao);

        Movie movie = new Movie();
        movie.setId(1L);
        defaultReviewService.enrich(movie);

        assertEquals(getTestReviews(), movie.getReviews());
        verify(mockReviewDao, times(1)).getByMovieId(1L);
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