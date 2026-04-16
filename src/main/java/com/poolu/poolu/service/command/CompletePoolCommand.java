package com.poolu.poolu.service.command;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import com.poolu.poolu.repository.PoolRepository;
import com.poolu.poolu.service.RideService;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CompletePoolCommand implements PoolCommand<PoolLifecycleCommandRequest> {

    private final PoolRepository poolRepository;
    private final RideService rideService;

    public CompletePoolCommand(PoolRepository poolRepository, RideService rideService) {
        this.poolRepository = poolRepository;
        this.rideService = rideService;
    }

    @Override
    public Class<PoolLifecycleCommandRequest> requestType() {
        return PoolLifecycleCommandRequest.class;
    }

    @Override
    public boolean supports(PoolLifecycleCommandRequest request) {
        return "complete".equalsIgnoreCase(request.action());
    }

    @Override
    public Pool execute(PoolLifecycleCommandRequest request) {
        Pool pool = poolRepository.findById(request.poolId())
                .orElseThrow(() -> new PoolNotFoundException("Pool not found"));
        if (pool.getStatus() != PoolStatus.IN_PROGRESS) {
            throw new RuntimeException("Only in-progress pools can move to COMPLETED");
        }

        pool.setStatus(PoolStatus.COMPLETED);
        Pool savedPool = poolRepository.save(pool);
        rideService.completeRide(savedPool.getPoolId());
        return savedPool;
    }
}
