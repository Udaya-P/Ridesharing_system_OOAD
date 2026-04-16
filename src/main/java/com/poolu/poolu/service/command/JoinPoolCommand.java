package com.poolu.poolu.service.command;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import com.poolu.poolu.model.model.Pooler;
import com.poolu.poolu.repository.PoolRepository;
import com.poolu.poolu.repository.PoolerRepository;
import com.poolu.poolu.service.exception.DuplicateJoinException;
import com.poolu.poolu.service.exception.PoolFullException;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import com.poolu.poolu.service.exception.PoolerNotFoundException;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
public class JoinPoolCommand implements PoolCommand<JoinPoolCommandRequest> {

    private final PoolRepository poolRepository;
    private final PoolerRepository poolerRepository;

    public JoinPoolCommand(PoolRepository poolRepository, PoolerRepository poolerRepository) {
        this.poolRepository = poolRepository;
        this.poolerRepository = poolerRepository;
    }

    @Override
    public Class<JoinPoolCommandRequest> requestType() {
        return JoinPoolCommandRequest.class;
    }

    @Override
    public Pool execute(JoinPoolCommandRequest request) {
        if (request.poolId() == null || request.poolId().isBlank()) {
            throw new PoolNotFoundException("Pool not found");
        }
        if (request.poolerId() == null || request.poolerId().isBlank()) {
            throw new RuntimeException("Pooler ID is required");
        }

        Pool pool = poolRepository.findById(request.poolId())
                .orElseThrow(() -> new PoolNotFoundException("Pool not found"));
        if (pool.getStatus() != PoolStatus.OPEN) {
            throw new RuntimeException("Pool is not open for joining");
        }

        Pooler pooler = poolerRepository.findById(request.poolerId())
                .orElseThrow(() -> new PoolerNotFoundException("Pooler not found"));
        if (pool.getPoolers() == null) {
            pool.setPoolers(new LinkedHashSet<>());
        }
        if (pool.getPoolers().stream().anyMatch(existingPooler -> existingPooler.getUserId().equals(request.poolerId()))) {
            throw new DuplicateJoinException("Pooler already joined this pool");
        }
        if (pool.getCapacity() != null && pool.getPoolers().size() >= pool.getCapacity()) {
            throw new PoolFullException("Pool is full");
        }

        pool.getPoolers().add(pooler);
        if (pool.getCapacity() != null && pool.getPoolers().size() >= pool.getCapacity()) {
            pool.setStatus(PoolStatus.FULL);
        }
        return poolRepository.save(pool);
    }
}
