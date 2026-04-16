package com.poolu.poolu.builder;

import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Location;
import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import com.poolu.poolu.model.model.Pooler;

import java.util.LinkedHashSet;
import java.util.Set;

public final class PoolBuilder {

    private Driver driver;
    private Location origin;
    private Location destination;
    private Integer capacity;
    private PoolStatus status = PoolStatus.OPEN;
    private Set<Pooler> poolers = new LinkedHashSet<>();

    private PoolBuilder() {
    }

    public static PoolBuilder aPool() {
        return new PoolBuilder();
    }

    public PoolBuilder withDriver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public PoolBuilder withOrigin(Location origin) {
        this.origin = origin;
        return this;
    }

    public PoolBuilder withDestination(Location destination) {
        this.destination = destination;
        return this;
    }

    public PoolBuilder withCapacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public PoolBuilder withStatus(PoolStatus status) {
        this.status = status;
        return this;
    }

    public PoolBuilder withPoolers(Set<Pooler> poolers) {
        this.poolers = poolers == null ? new LinkedHashSet<>() : new LinkedHashSet<>(poolers);
        return this;
    }

    public Pool build() {
        Pool pool = new Pool();
        pool.setDriver(driver);
        pool.setOrigin(origin);
        pool.setDestination(destination);
        pool.setCapacity(capacity);
        pool.setStatus(status);
        pool.setPoolers(new LinkedHashSet<>(poolers));
        return pool;
    }
}
