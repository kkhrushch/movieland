package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.holder.CurrentUserHolder;
import com.khrushch.movieland.model.Review;
import com.khrushch.movieland.model.UserRole;
import com.khrushch.movieland.rest.v1.annotation.ProtectedBy;
import com.khrushch.movieland.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private ReviewService reviewService;

    @PostMapping
    @ProtectedBy(acceptedRoles = UserRole.USER)
    public void addMovie(@RequestBody Review review) {
        review.setUser(CurrentUserHolder.getUser());
        reviewService.addReview(review);
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
