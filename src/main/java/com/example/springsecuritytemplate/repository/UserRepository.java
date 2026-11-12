package com.example.springsecuritytemplate.repository;

import com.example.springsecuritytemplate.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUserName(String userName);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUserNameOrEmail(String userName, String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}


//Method	Purpose
//findByUsername()	Find user by username.
//findByEmail()	Find user by email.
//findByUsernameOrEmail()	Find user using either login identifier.
//existsByUsername()	Check duplicate username during registration.
//existsByEmail()	Check duplicate email during registration