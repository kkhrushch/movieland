package com.khrushch.movieland.rest.v1.controller;

import com.khrushch.movieland.dto.ReviewDto;
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
    public void addMovie(@RequestBody ReviewDto reviewDto, @RequestHeader("uuid") String uuid) {
        reviewDto.setUuid(uuid);
        reviewService.addReview(reviewDto);
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}
