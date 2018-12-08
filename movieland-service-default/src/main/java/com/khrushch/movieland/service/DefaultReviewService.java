package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.ReviewDao;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReviewService implements ReviewService {
    private ReviewDao reviewDao;

    @Override
    public void addReview(Review review) {
        reviewDao.addReview(review);
    }

    @Override
    public void enrich(Movie movie) {
        List<Review> reviews = reviewDao.getByMovieId(movie.getId());
        movie.setReviews(reviews);
    }

    @Autowired
    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

}
