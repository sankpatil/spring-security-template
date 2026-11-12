
package com.example.springsecuritytemplate.service;

import com.example.springsecuritytemplate.exception.ResourceAlreadyExistsException;
import com.example.springsecuritytemplate.model.AppRole;
import com.example.springsecuritytemplate.model.Role;
import com.example.springsecuritytemplate.model.User;

import com.example.springsecuritytemplate.repository.RoleRepository;
import com.example.springsecuritytemplate.repository.UserRepository;

import com.example.springsecuritytemplate.security.CustomUserDetails;
import com.example.springsecuritytemplate.security.JwtUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // Register new user
    @Transactional
    public User registerUser(
            String username,
            String email,
            String password
    ) {

        username = username.trim();
        email = email.trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByUserName(username)) {
            throw new ResourceAlreadyExistsException("Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email is already registered!");
        }

        User user = new User();

        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Assign default USER role
        Role userRole = roleRepository
                .findByRoleName(AppRole.ROLE_USER)
                .orElseThrow(() ->
                        new IllegalStateException("Default role not found")
                );

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        user.setRoles(roles);

        return userRepository.save(user);
    }

    // Login user
    public ResponseCookie loginUser(
            String usernameOrEmail,
            String password
    ) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usernameOrEmail,
                        password
                )
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return jwtUtils.generateJwtCookie(userDetails.getUser());
    }
}
