package com.projects.gym_management.repositories;

import java.util.Optional;

import com.projects.gym_management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}