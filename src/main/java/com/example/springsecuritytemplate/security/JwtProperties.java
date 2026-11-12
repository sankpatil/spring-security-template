package com.example.springsecuritytemplate.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.app")
@Getter
@Setter
public class JwtProperties {

    private String jwtSecret;
    private long jwtExpirationMs;
    private String jwtCookieName;
    private String jwtCookiePath;
    private boolean jwtCookieHttpOnly;
    private boolean jwtCookieSecure;
    private String jwtCookieSameSite;

    // Generate getters and setters using IntelliJ.
}