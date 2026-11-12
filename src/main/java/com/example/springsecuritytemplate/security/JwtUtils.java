
package com.example.springsecuritytemplate.security;

import com.example.springsecuritytemplate.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    @Value("${spring.app.jwtCookieName}")
    private String jwtCookieName;

    @Value("${spring.app.jwtCookiePath}")
    private String jwtCookiePath;

    @Value("${spring.app.jwtCookieSecure:false}")
    private boolean jwtCookieSecure;

    @Value("${spring.app.jwtCookieHttpOnly:true}")
    private boolean jwtCookieHttpOnly;

    @Value("${spring.app.jwtCookieSameSite:Lax}")
    private String jwtCookieSameSite;

    // Convert Base64 secret into a signing key
    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate JWT token
    public String generateJwtToken(User user) {

        Date issuedAt = new Date();

        Date expiration = new Date(
                issuedAt.getTime() + jwtExpirationMs
        );

        return Jwts.builder()
                .subject(user.getUserName())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    // Create JWT cookie
    public ResponseCookie generateJwtCookie(User user) {

        String jwt = generateJwtToken(user);

        return ResponseCookie.from(jwtCookieName, jwt)
                .httpOnly(jwtCookieHttpOnly)
                .secure(jwtCookieSecure)
                .path(jwtCookiePath)
                .maxAge(jwtExpirationMs / 1000)
                .sameSite(jwtCookieSameSite)
                .build();
    }

    // Extract JWT from request cookie
    public String getJwtFromCookies(HttpServletRequest request) {

        Cookie cookie = WebUtils.getCookie(
                request,
                jwtCookieName
        );

        return cookie != null ? cookie.getValue() : null;
    }

    // Extract username from JWT
    public String getUserNameFromJwtToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Validate JWT token
    public boolean validateJwtToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

    // Clear JWT cookie during logout
    public ResponseCookie getCleanJwtCookie() {

        return ResponseCookie.from(jwtCookieName, "")
                .httpOnly(jwtCookieHttpOnly)
                .secure(jwtCookieSecure)
                .path(jwtCookiePath)
                .maxAge(0)
                .sameSite(jwtCookieSameSite)
                .build();
    }
}
