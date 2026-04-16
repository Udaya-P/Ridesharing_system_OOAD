package com.poolu.poolu.service;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.Ride;

public interface RideService {
    Ride startRide(Pool pool);

    Ride completeRide(String poolId);

    Ride getRideByPoolId(String poolId);
}
