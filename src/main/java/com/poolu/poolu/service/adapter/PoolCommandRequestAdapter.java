package com.poolu.poolu.service.adapter;

import com.poolu.poolu.controller.dto.JoinPoolRequest;
import com.poolu.poolu.service.command.JoinPoolCommandRequest;
import com.poolu.poolu.service.command.PoolLifecycleCommandRequest;
import org.springframework.stereotype.Component;

@Component
public class PoolCommandRequestAdapter {

    public JoinPoolCommandRequest adapt(JoinPoolRequest request) {
        return new JoinPoolCommandRequest(request.poolId(), request.poolerId());
    }

    public PoolLifecycleCommandRequest adapt(String poolId, String action) {
        return new PoolLifecycleCommandRequest(poolId, action);
    }
}
