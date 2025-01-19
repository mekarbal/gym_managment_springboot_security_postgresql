package com.projects.gym_management.repositories;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

import com.projects.gym_management.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE " +
            "(:name IS NULL OR LOWER(u.firstname) LIKE LOWER(:name || '%')) AND " +
            "(:cin IS NULL OR LOWER(CAST(u.cin AS TEXT)) LIKE LOWER('%' || :cin || '%'))",
            countQuery = "SELECT COUNT(*) FROM users u WHERE " +
                    "(:name IS NULL OR LOWER(u.firstname) LIKE LOWER(:name || '%')) AND " +
                    "(:cin IS NULL OR LOWER(CAST(u.cin AS TEXT)) LIKE LOWER('%' || :cin || '%'))",
            nativeQuery = true)
    Page<User> findBySearchCriteria(
            @Param("name") String name,
            @Param("cin") String cin,
            Pageable pageable
    );

}