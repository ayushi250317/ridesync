package com.app.ridesync.repositories;
import java.util.Optional;

import com.app.ridesync.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Integer id);
}