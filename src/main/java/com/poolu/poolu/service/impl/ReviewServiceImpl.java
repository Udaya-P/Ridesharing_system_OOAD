package com.poolu.poolu.service.impl;

import com.poolu.poolu.builder.ReviewBuilder;
import com.poolu.poolu.model.model.Review;
import com.poolu.poolu.repository.ReviewRepository;
import com.poolu.poolu.repository.UserRepository;
import com.poolu.poolu.service.ReviewService;
import com.poolu.poolu.service.UserLookupFacade;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final UserLookupFacade userLookupFacade;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            UserLookupFacade userLookupFacade
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.userLookupFacade = userLookupFacade;
    }

    @Override
    @Transactional
    public Review submitReview(String reviewerId, String revieweeId, int stars, String comment) {
        Review review = ReviewBuilder.aReview()
                .withReviewer(userLookupFacade.findById(reviewerId))
                .withReviewee(userLookupFacade.findById(revieweeId))
                .withStars(stars)
                .withComment(comment)
                .build();

        Review savedReview = reviewRepository.save(review);
        calculateKarmaScore(revieweeId);
        return savedReview;
    }

    @Override
    public List<Review> getReviewsForUser(String userId) {
        return reviewRepository.findByRevieweeUserId(userId);
    }

    @Override
    public Float calculateKarmaScore(String userId) {
        List<Review> reviews = reviewRepository.findByRevieweeUserId(userId);
        if (reviews.isEmpty()) {
            return 0.0f;
        }

        float karma = (float) reviews.stream()
                .mapToInt(Review::getStars)
                .average()
                .orElse(0.0);

        userRepository.findById(userId).ifPresent(user -> {
            user.setKarmaScore(karma);
            userRepository.save(user);
        });
        return karma;
    }
}
