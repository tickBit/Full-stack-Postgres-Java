package com.example.backend.repository;


import com.example.backend.model.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE username = :username", nativeQuery = true)
    void deleteByUsernameNative(String username);

}