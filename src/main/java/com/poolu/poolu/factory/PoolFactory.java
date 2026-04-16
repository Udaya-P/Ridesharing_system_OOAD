package com.poolu.poolu.factory;

import com.poolu.poolu.builder.PoolBuilder;
import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Location;
import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import org.springframework.stereotype.Component;

@Component
public class PoolFactory {

    public Pool create(Driver driver, Location origin, Location destination) {
        if (driver.getVehicleInfo() == null || driver.getVehicleInfo().getSeats() == null) {
            throw new RuntimeException("Driver vehicle seats are required");
        }

        return PoolBuilder.aPool()
                .withDriver(driver)
                .withOrigin(origin)
                .withDestination(destination)
                .withCapacity(Math.max(driver.getVehicleInfo().getSeats() - 1, 0))
                .withStatus(PoolStatus.OPEN)
                .build();
    }
}
