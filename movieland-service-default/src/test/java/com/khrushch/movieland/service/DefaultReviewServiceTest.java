package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.ReviewDao;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultReviewServiceTest {
    @Mock
    private ReviewDao mockReviewDao;

    @InjectMocks
    private DefaultReviewService defaultReviewService;

    @Test
    public void testEnrich() {
        when(mockReviewDao.getByMovieId(1L)).thenReturn(getTestReviews());

        Movie movie = new Movie();
        movie.setId(1L);
        defaultReviewService.enrich(movie);

        assertEquals(getTestReviews(), movie.getReviews());
        verify(mockReviewDao, times(1)).getByMovieId(1L);
    }

    @Test
    public void testAddReview() {
        Review review = new Review();
        review.setMovieId(1);
        review.setText("aText");
        User user = new User();
        user.setId(1);
        user.setRole("USER");
        review.setUser(user);

        defaultReviewService.addReview(review);

        verify(mockReviewDao, times(1)).addReview(review);
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