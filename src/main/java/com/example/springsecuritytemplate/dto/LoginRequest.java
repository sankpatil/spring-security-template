
package com.example.springsecuritytemplate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username or email is required")
    @Size(max = 100, message = "Username or email is too long")
    private String usernameOrEmail;

    @NotBlank(message = "Password is required")
    @Size(max = 100, message = "Password is too long")
    private String password;
}
