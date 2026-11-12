package com.example.springsecuritytemplate.repository;

import com.example.springsecuritytemplate.model.AppRole;
import com.example.springsecuritytemplate.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(AppRole roleName);

    boolean existsByRoleName(AppRole roleName);
}
