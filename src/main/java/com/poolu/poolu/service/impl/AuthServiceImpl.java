package com.poolu.poolu.service.impl;

import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Pooler;
import com.poolu.poolu.repository.DriverRepository;
import com.poolu.poolu.repository.PoolerRepository;
import com.poolu.poolu.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final DriverRepository driverRepository;
    private final PoolerRepository poolerRepository;

    public AuthServiceImpl(DriverRepository driverRepository, PoolerRepository poolerRepository) {
        this.driverRepository = driverRepository;
        this.poolerRepository = poolerRepository;
    }

    @Override
    public Driver registerDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Pooler registerPooler(Pooler pooler) {
        return poolerRepository.save(pooler);
    }

    @Override
    public Object login(String email, String password) {
        // JWT implementation comes later
        return null;
    }
}
