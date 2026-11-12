package com.example.springsecuritytemplate.config;


import com.example.springsecuritytemplate.model.AppRole;
import com.example.springsecuritytemplate.model.Role;
import com.example.springsecuritytemplate.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @Bean
    public CommandLineRunner initializeRoles() {

        return args -> {

            for (AppRole appRole : AppRole.values()) {

                if (!roleRepository.existsByRoleName(appRole)) {

                    Role role = new Role(appRole);

                    roleRepository.save(role);
                }
            }

        };
    }
}

