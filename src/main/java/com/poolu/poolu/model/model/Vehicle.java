package com.poolu.poolu.model.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vehicleId;

    private String make;
    private String model;
    private Integer seats;
    private String plateNumber;
}