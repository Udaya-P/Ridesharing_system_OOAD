package com.poolu.poolu.model.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String locationId;

    private Double latitude;
    private Double longitude;
    private String address;
}