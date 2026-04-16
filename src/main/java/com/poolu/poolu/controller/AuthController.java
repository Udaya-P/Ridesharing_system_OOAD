package com.poolu.poolu.controller;

import com.poolu.poolu.controller.dto.LoginRequest;
import com.poolu.poolu.model.model.Driver;
import com.poolu.poolu.model.model.Pooler;
import com.poolu.poolu.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/drivers/register")
    public Driver registerDriver(@Valid @RequestBody Driver driver) {
        return authService.registerDriver(driver);
    }

    @PostMapping("/poolers/register")
    public Pooler registerPooler(@Valid @RequestBody Pooler pooler) {
        return authService.registerPooler(pooler);
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.email(), loginRequest.password());
    }
}
