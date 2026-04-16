package com.poolu.poolu.repository;

import com.poolu.poolu.model.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByRevieweeUserId(String userId);
}
