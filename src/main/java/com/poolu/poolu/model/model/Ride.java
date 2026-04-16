package com.poolu.poolu.model.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String rideId;

    @ManyToOne
    @JoinColumn(name = "pool_id")
    private Pool pool;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Float fare;

    @Enumerated(EnumType.STRING)
    private RideStatus status = RideStatus.SCHEDULED;
}