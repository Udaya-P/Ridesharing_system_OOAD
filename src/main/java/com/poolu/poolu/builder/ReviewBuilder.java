package com.poolu.poolu.builder;

import com.poolu.poolu.model.model.Review;
import com.poolu.poolu.model.model.User;

public final class ReviewBuilder {

    private User reviewer;
    private User reviewee;
    private Integer stars;
    private String comment;

    private ReviewBuilder() {
    }

    public static ReviewBuilder aReview() {
        return new ReviewBuilder();
    }

    public ReviewBuilder withReviewer(User reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public ReviewBuilder withReviewee(User reviewee) {
        this.reviewee = reviewee;
        return this;
    }

    public ReviewBuilder withStars(Integer stars) {
        this.stars = stars;
        return this;
    }

    public ReviewBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Review build() {
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setStars(stars);
        review.setComment(comment);
        return review;
    }
}
