package com.poolu.poolu.controller;

import com.poolu.poolu.controller.dto.ReviewRequest;
import com.poolu.poolu.model.model.Review;
import com.poolu.poolu.service.ReviewFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewFacade reviewFacade;

    public ReviewController(ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
    }

    @PostMapping
    public Review submitReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        return reviewFacade.submitReview(reviewRequest);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsForUser(@PathVariable String userId) {
        return reviewFacade.getReviewsForUser(userId);
    }

    @GetMapping("/user/{userId}/karma")
    public Map<String, Float> calculateKarmaScore(@PathVariable String userId) {
        return reviewFacade.getKarmaScore(userId);
    }
}
