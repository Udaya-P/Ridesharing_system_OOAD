package com.poolu.poolu.service;

import com.poolu.poolu.model.model.User;
import com.poolu.poolu.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserLookupFacade {

    private final UserRepository userRepository;

    public UserLookupFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
