
package com.example.springsecuritytemplate.security;

import com.example.springsecuritytemplate.model.User;
import com.example.springsecuritytemplate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUserNameOrEmail(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with username or email: " + username
                        )
                );

        return new CustomUserDetails(user);
    }
}
