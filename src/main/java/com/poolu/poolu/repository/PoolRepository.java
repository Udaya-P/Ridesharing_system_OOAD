package com.poolu.poolu.repository;

import com.poolu.poolu.model.model.Pool;
import com.poolu.poolu.model.model.PoolStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoolRepository extends JpaRepository<Pool, String> {
    List<Pool> findByStatus(PoolStatus status);
}
