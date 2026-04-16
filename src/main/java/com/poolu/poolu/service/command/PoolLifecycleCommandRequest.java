package com.poolu.poolu.service.command;

public record PoolLifecycleCommandRequest(String poolId, String action) implements PoolCommandRequest {
}
