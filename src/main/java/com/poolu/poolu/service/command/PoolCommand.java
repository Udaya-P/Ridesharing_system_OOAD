package com.poolu.poolu.service.command;

import com.poolu.poolu.model.model.Pool;

public interface PoolCommand<R extends PoolCommandRequest> {
    Class<R> requestType();

    default boolean supports(R request) {
        return true;
    }

    Pool execute(R request);
}
