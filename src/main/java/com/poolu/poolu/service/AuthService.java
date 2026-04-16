package com.poolu.poolu.service;

import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Pooler;

public interface AuthService {
    Driver registerDriver(Driver driver);
    Pooler registerPooler(Pooler pooler);
    Object login(String email, String password);
}
