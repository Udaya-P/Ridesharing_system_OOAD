package com.poolu.poolu.repository;

import com.poolu.poolu.model.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, String> {
    Optional<Ride> findByPoolPoolId(String poolId);
}
