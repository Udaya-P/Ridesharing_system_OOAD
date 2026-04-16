package com.poolu.poolu.repository;

import com.poolu.poolu.model.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
