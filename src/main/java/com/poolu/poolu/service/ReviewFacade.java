package com.poolu.poolu.service;

import com.poolu.poolu.controller.dto.ReviewRequest;
import com.poolu.poolu.model.model.Review;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReviewFacade {

    private final ReviewService reviewService;

    public ReviewFacade(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public Review submitReview(ReviewRequest request) {
        return reviewService.submitReview(
                request.reviewerId(),
                request.revieweeId(),
                request.stars(),
                request.comment()
        );
    }

    public List<Review> getReviewsForUser(String userId) {
        return reviewService.getReviewsForUser(userId);
    }

    public Map<String, Float> getKarmaScore(String userId) {
        return Map.of("karmaScore", reviewService.calculateKarmaScore(userId));
    }
}
