package com.poolu.poolu.service.command;

public record JoinPoolCommandRequest(String poolId, String poolerId) implements PoolCommandRequest {
}
