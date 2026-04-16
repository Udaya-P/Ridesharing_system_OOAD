package com.poolu.poolu.controller;

import com.poolu.poolu.model.model.Ride;
import com.poolu.poolu.service.RideService;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping("/pool/{poolId}")
    public Map<String, Object> getRideByPoolId(@PathVariable String poolId) {
        Ride ride = rideService.getRideByPoolId(poolId);
        return Map.of(
                "rideId", ride.getRideId(),
                "poolId", ride.getPool().getPoolId(),
                "status", ride.getStatus(),
                "startTime", ride.getStartTime(),
                "endTime", ride.getEndTime()
        );
    }

    @ExceptionHandler(PoolNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(PoolNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", exception.getMessage()));
    }
}
