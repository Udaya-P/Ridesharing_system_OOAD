package com.poolu.poolu.service.command;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import com.poolu.poolu.repository.PoolRepository;
import com.poolu.poolu.service.RideService;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class StartPoolCommand implements PoolCommand<PoolLifecycleCommandRequest> {

    private final PoolRepository poolRepository;
    private final RideService rideService;

    public StartPoolCommand(PoolRepository poolRepository, RideService rideService) {
        this.poolRepository = poolRepository;
        this.rideService = rideService;
    }

    @Override
    public Class<PoolLifecycleCommandRequest> requestType() {
        return PoolLifecycleCommandRequest.class;
    }

    @Override
    public boolean supports(PoolLifecycleCommandRequest request) {
        return "start".equalsIgnoreCase(request.action());
    }

    @Override
    public Pool execute(PoolLifecycleCommandRequest request) {
        Pool pool = poolRepository.findById(request.poolId())
                .orElseThrow(() -> new PoolNotFoundException("Pool not found"));
        if (pool.getStatus() != PoolStatus.FULL) {
            throw new RuntimeException("Only full pools can move to IN_PROGRESS");
        }

        pool.setStatus(PoolStatus.IN_PROGRESS);
        Pool savedPool = poolRepository.save(pool);
        rideService.startRide(savedPool);
        return savedPool;
    }
}
