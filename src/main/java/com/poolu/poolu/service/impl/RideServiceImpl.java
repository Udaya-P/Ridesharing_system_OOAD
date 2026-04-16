package com.poolu.poolu.service.impl;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.Ride;
import com.poolu.poolu.model.model.RideStatus;
import com.poolu.poolu.repository.RideRepository;
import com.poolu.poolu.service.RideService;
import com.poolu.poolu.service.exception.PoolNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public Ride startRide(Pool pool) {
        Ride ride = rideRepository.findByPoolPoolId(pool.getPoolId()).orElseGet(Ride::new);
        ride.setPool(pool);
        ride.setStartTime(LocalDateTime.now());
        ride.setEndTime(null);
        ride.setStatus(RideStatus.IN_PROGRESS);
        return rideRepository.save(ride);
    }

    @Override
    public Ride completeRide(String poolId) {
        Ride ride = getRideByPoolId(poolId);
        ride.setEndTime(LocalDateTime.now());
        ride.setStatus(RideStatus.COMPLETED);
        return rideRepository.save(ride);
    }

    @Override
    public Ride getRideByPoolId(String poolId) {
        return rideRepository.findByPoolPoolId(poolId)
                .orElseThrow(() -> new PoolNotFoundException("Ride not found for pool"));
    }
}
