package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.User;
import com.khrushch.movieland.rest.v1.holder.CurrentUserHolder;
import com.khrushch.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    ReviewService reviewService;

    @PostMapping
    public void addMovie(@RequestBody Review review) {
        review.setUser(CurrentUserHolder.getUser());
        reviewService.addReview(review);
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
