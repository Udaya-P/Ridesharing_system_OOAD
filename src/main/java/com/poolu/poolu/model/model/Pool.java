package com.poolu.poolu.model.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "pools")
public class Pool {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String poolId;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToMany
    @JoinTable(
        name = "pool_poolers",
        joinColumns = @JoinColumn(name = "pool_id"),
        inverseJoinColumns = @JoinColumn(name = "pooler_id")
    )
    private Set<Pooler> poolers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origin_id")
    private Location origin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private Location destination;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private PoolStatus status = PoolStatus.OPEN;

    @Transient
    public int getCurrentPoolerCount() {
        return poolers == null ? 0 : poolers.size();
    }
}
