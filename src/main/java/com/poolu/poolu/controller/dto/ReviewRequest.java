package com.poolu.poolu.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewRequest(
        @NotBlank(message = "Reviewer ID is required")
        String reviewerId,
        @NotBlank(message = "Reviewee ID is required")
        String revieweeId,
        @NotNull(message = "Stars are required")
        @Min(value = 1, message = "Stars must be at least 1")
        @Max(value = 5, message = "Stars must be at most 5")
        Integer stars,
        @NotBlank(message = "Comment is required")
        @Size(max = 500, message = "Comment must be at most 500 characters")
        String comment
) {
}
