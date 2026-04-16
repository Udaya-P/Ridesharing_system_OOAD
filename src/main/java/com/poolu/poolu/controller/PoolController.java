package com.poolu.poolu.controller;

import com.poolu.poolu.controller.dto.JoinPoolRequest;
import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.service.PoolService;
import com.poolu.poolu.service.adapter.PoolCommandRequestAdapter;
import com.poolu.poolu.service.exception.DuplicateJoinException;
import com.poolu.poolu.service.exception.PoolFullException;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import com.poolu.poolu.service.exception.PoolerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pools")
public class PoolController {

    private final PoolService poolService;
    private final PoolCommandRequestAdapter poolCommandRequestAdapter;

    public PoolController(PoolService poolService, PoolCommandRequestAdapter poolCommandRequestAdapter) {
        this.poolService = poolService;
        this.poolCommandRequestAdapter = poolCommandRequestAdapter;
    }

    @PostMapping
    public Pool createPool(@RequestBody Pool pool) {
        return poolService.createPool(pool);
    }

    @PostMapping("/join")
    public Map<String, String> joinPool(@Valid @RequestBody JoinPoolRequest joinPoolRequest) {
        Pool joinedPool = poolService.joinPool(poolCommandRequestAdapter.adapt(joinPoolRequest));
        return Map.of(
                "message", "Pool joined successfully",
                "poolId", joinedPool.getPoolId()
        );
    }

    @PostMapping("/{poolId}/start")
    public Map<String, String> startPool(@PathVariable String poolId) {
        Pool startedPool = poolService.startPool(poolCommandRequestAdapter.adapt(poolId, "start"));
        return Map.of(
                "message", "Pool started successfully",
                "poolId", startedPool.getPoolId()
        );
    }

    @PostMapping("/{poolId}/complete")
    public Map<String, String> completePool(@PathVariable String poolId) {
        Pool completedPool = poolService.completePool(poolCommandRequestAdapter.adapt(poolId, "complete"));
        return Map.of(
                "message", "Pool completed successfully",
                "poolId", completedPool.getPoolId()
        );
    }

    @GetMapping
    public List<Pool> getAvailablePools() {
        return poolService.getAvailablePools();
    }

    @GetMapping("/{poolId}")
    public Pool getPoolById(@PathVariable String poolId) {
        return poolService.getPoolById(poolId);
    }

    @ExceptionHandler({PoolNotFoundException.class, PoolerNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler({PoolFullException.class, DuplicateJoinException.class})
    public ResponseEntity<Map<String, String>> handleConflict(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", exception.getMessage()));
    }
}
