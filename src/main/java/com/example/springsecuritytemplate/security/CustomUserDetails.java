
package com.example.springsecuritytemplate.security;

import com.example.springsecuritytemplate.model.User;
import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    @Getter
    private final Long userId;

    private final String userName;

    @Getter
    private final String email;

    private final String password;

    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {

        this.user = user;

        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();

        this.enabled = Boolean.TRUE.equals(user.getEnabled());

        this.authorities = user.getRoles() == null
                ? Collections.emptyList()
                : user.getRoles()
                  .stream()
                  .map(role ->
                       new SimpleGrantedAuthority(
                               role.getRoleName().name()
                       )
                  )
                  .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public User getUser() {
        return user;
    }
}
