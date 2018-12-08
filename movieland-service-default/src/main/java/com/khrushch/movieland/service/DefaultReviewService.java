package com.khrushch.movieland.service;

import com.khrushch.movieland.dao.ReviewDao;
import com.khrushch.movieland.holder.CurrentUserHolder;
import com.khrushch.movieland.model.Movie;
import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.model.UserRole;
import com.khrushch.movieland.service.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReviewService implements ReviewService {
    private ReviewDao reviewDao;

    @Override
    public void addReview(Review review) {
        User user = CurrentUserHolder.getUser();
        if (user == null) {
            throw new AuthenticationException("Not authorized, user not found");
        } else if (user.getRole() != UserRole.USER){
            throw new AuthenticationException("Not enough privileges");
        }

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
