package com.poolu.poolu.service.pricing;

import com.poolu.poolu.model.model.Location;
import com.poolu.poolu.model.model.Pool;
import org.springframework.stereotype.Component;

@Component
public class DistanceBasedPricing {

    private static final float RATE_PER_KM = 10.0f;

    public float calculate(Pool pool) {
        return calculateDistanceKm(pool.getOrigin(), pool.getDestination()) * RATE_PER_KM;
    }

    public float calculateDistanceKm(Location origin, Location destination) {
        if (origin == null || destination == null
                || origin.getLatitude() == null || origin.getLongitude() == null
                || destination.getLatitude() == null || destination.getLongitude() == null) {
            throw new RuntimeException("Origin and destination coordinates are required");
        }

        double earthRadiusKm = 6371.0;
        double latDistance = Math.toRadians(destination.getLatitude() - origin.getLatitude());
        double lonDistance = Math.toRadians(destination.getLongitude() - origin.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(origin.getLatitude())) * Math.cos(Math.toRadians(destination.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (float) (earthRadiusKm * c);
    }
}
