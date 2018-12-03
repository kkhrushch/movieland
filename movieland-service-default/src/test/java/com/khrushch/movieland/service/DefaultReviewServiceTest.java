package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.ReviewDao;
import com.khrushch.movieland.dto.ReviewDto;
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

    @Mock
    private SecurityService mockSecurityService;

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
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUuid("uuid");
        reviewDto.setMovieId(1);
        reviewDto.setText("aText");

        User user = new User(1, "aNickname");
        when(mockSecurityService.getUserByUuid(reviewDto.getUuid())).thenReturn(user);

        defaultReviewService.addReview(reviewDto);

        Review expectedReview = new Review();
        expectedReview.setMovieId(reviewDto.getMovieId());
        expectedReview.setUser(user);
        expectedReview.setText(reviewDto.getText());

        verify(mockSecurityService, times(1)).getUserByUuid(reviewDto.getUuid());
        verify(mockReviewDao, times(1)).addReview(expectedReview);
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