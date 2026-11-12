
package com.example.springsecuritytemplate.util;

import com.example.springsecuritytemplate.model.User;
import com.example.springsecuritytemplate.security.CustomUserDetails;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    // Get the current authentication object
    public Authentication getAuthentication() {

        return SecurityContextHolder.getContext().getAuthentication();
    }

    // Get the currently authenticated user's details
    public CustomUserDetails getCurrentUserDetails() {

        Authentication authentication = getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            throw new IllegalStateException("User is not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("Invalid authenticated user");
        }

        return userDetails;
    }

    // Get the complete logged-in User entity
    public User getLoggedInUser() {

        return getCurrentUserDetails().getUser();
    }

    // Get the logged-in user's ID
    public Long getLoggedInUserId() {

        return getCurrentUserDetails().getUserId();
    }

    // Get the logged-in user's username
    public String getLoggedInUsername() {

        return getCurrentUserDetails().getUsername();
    }

    // Get the logged-in user's email
    public String getLoggedInEmail() {

        return getCurrentUserDetails().getEmail();
    }
}
