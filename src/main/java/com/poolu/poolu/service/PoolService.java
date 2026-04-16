package com.poolu.poolu.service;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.service.command.JoinPoolCommandRequest;
import com.poolu.poolu.service.command.PoolLifecycleCommandRequest;

import java.util.List;

public interface PoolService {
    Pool createPool(Pool pool);
    Pool joinPool(JoinPoolCommandRequest request);
    Pool startPool(PoolLifecycleCommandRequest request);
    Pool completePool(PoolLifecycleCommandRequest request);
    List<Pool> getAvailablePools();
    Pool getPoolById(String poolId);
}
