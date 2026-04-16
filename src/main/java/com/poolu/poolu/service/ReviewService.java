package com.poolu.poolu.service;

import com.poolu.poolu.model.model.Review;

import java.util.List;

public interface ReviewService {
    Review submitReview(String reviewerId, String revieweeId, int stars, String comment);
    List<Review> getReviewsForUser(String userId);
    Float calculateKarmaScore(String userId);
}
