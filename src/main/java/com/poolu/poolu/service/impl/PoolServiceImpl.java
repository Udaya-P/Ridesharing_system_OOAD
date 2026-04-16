package com.poolu.poolu.service.impl;

import com.poolu.poolu.factory.PoolFactory;
import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import com.poolu.poolu.repository.DriverRepository;
import com.poolu.poolu.repository.PoolRepository;
import com.poolu.poolu.service.PoolService;
import com.poolu.poolu.service.command.JoinPoolCommandRequest;
import com.poolu.poolu.service.command.PoolCommandInvoker;
import com.poolu.poolu.service.command.PoolLifecycleCommandRequest;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoolServiceImpl implements PoolService {

    private final PoolRepository poolRepository;
    private final DriverRepository driverRepository;
    private final PoolFactory poolFactory;
    private final PoolCommandInvoker poolCommandInvoker;

    public PoolServiceImpl(
            PoolRepository poolRepository,
            DriverRepository driverRepository,
            PoolFactory poolFactory,
            PoolCommandInvoker poolCommandInvoker
    ) {
        this.poolRepository = poolRepository;
        this.driverRepository = driverRepository;
        this.poolFactory = poolFactory;
        this.poolCommandInvoker = poolCommandInvoker;
    }

    @Override
    public Pool createPool(Pool poolRequest) {
        if (poolRequest.getDriver() == null || poolRequest.getDriver().getUserId() == null) {
            throw new RuntimeException("Driver ID is required");
        }

        Driver driver = driverRepository.findById(poolRequest.getDriver().getUserId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Pool pool = poolFactory.create(driver, poolRequest.getOrigin(), poolRequest.getDestination());
        return poolRepository.save(pool);
    }

    @Override
    public Pool joinPool(JoinPoolCommandRequest request) {
        return poolCommandInvoker.execute(request);
    }

    @Override
    public Pool startPool(PoolLifecycleCommandRequest request) {
        return poolCommandInvoker.execute(request);
    }

    @Override
    public Pool completePool(PoolLifecycleCommandRequest request) {
        return poolCommandInvoker.execute(request);
    }

    @Override
    public List<Pool> getAvailablePools() {
        return poolRepository.findAll();
    }

    @Override
    public Pool getPoolById(String poolId) {
        return poolRepository.findById(poolId)
                .orElseThrow(() -> new PoolNotFoundException("Pool not found"));
    }
}
