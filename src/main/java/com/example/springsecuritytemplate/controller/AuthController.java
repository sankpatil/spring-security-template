
package com.example.springsecuritytemplate.controller;

import com.example.springsecuritytemplate.dto.LoginRequest;
import com.example.springsecuritytemplate.dto.MessageResponse;
import com.example.springsecuritytemplate.dto.SignupRequest;
import com.example.springsecuritytemplate.dto.UserInfoResponse;
import com.example.springsecuritytemplate.model.User;
import com.example.springsecuritytemplate.security.JwtUtils;
import com.example.springsecuritytemplate.service.AuthService;
import com.example.springsecuritytemplate.util.AuthUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final AuthUtil authUtil;

    // Register user
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(
            @Valid @RequestBody SignupRequest request) {

        authService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("User registered successfully!"));
    }

    // Login user
    @PostMapping("/signin")
    public ResponseEntity<MessageResponse> loginUser(
            @Valid @RequestBody LoginRequest request) {

        ResponseCookie jwtCookie = authService.loginUser(
                request.getUsernameOrEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new MessageResponse("Login successful!"));
    }

    // Logout user
    @PostMapping("/signout")
    public ResponseEntity<MessageResponse> logoutUser() {

        ResponseCookie cleanCookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanCookie.toString())
                .body(new MessageResponse("Logout successful!"));
    }

    // Get current authenticated user
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getCurrentUser() {

        User user = authUtil.getLoggedInUser();

        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());

        UserInfoResponse response = new UserInfoResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                roles
        );

        return ResponseEntity.ok(response);
    }
}
