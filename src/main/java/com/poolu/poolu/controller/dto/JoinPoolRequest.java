package com.poolu.poolu.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record JoinPoolRequest(
        @NotBlank(message = "Pool ID is required")
        String poolId,
        @NotBlank(message = "Pooler ID is required")
        String poolerId
) {
}
