package com.hyrax.spring.boot.starter.authentication.jwt;

import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationAdminJWTProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationUserJWTProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTTokenGenerator {

    private static final String AUTHORITY_ADMIN = "ADMIN";
    private static final String AUTHORITY_USER = "USER";
    private static final String AUTHORITY_KEY = "authority";

    private final AuthenticationProperties authenticationProperties;
    private final AuthenticationAdminJWTProperties adminJWTProperties;
    private final AuthenticationUserJWTProperties userJWTProperties;

    @Autowired
    public JWTTokenGenerator(final AuthenticationProperties authenticationProperties,
                             final AuthenticationAdminJWTProperties adminJWTProperties, final AuthenticationUserJWTProperties userJWTProperties) {
        this.authenticationProperties = authenticationProperties;
        this.adminJWTProperties = adminJWTProperties;
        this.userJWTProperties = userJWTProperties;
    }

    public String generateToken(final Authentication authentication) {
        String token = null;

        final String authority = getAuthority(authentication);
        if (AUTHORITY_ADMIN.equalsIgnoreCase(authority)) {
            token = generateToken(authentication, adminJWTProperties.getExpirationTimeInMinutes());
        } else if (AUTHORITY_USER.equalsIgnoreCase(authority)) {
            token = generateToken(authentication, userJWTProperties.getExpirationTimeInMinutes());
        }

        return token;
    }

    private String generateToken(final Authentication authentication, final Long expirationTimeInMinutes) {
        return Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .claim(AUTHORITY_KEY, getAuthority(authentication))
                .setExpiration(createDateFromLocalDateTime(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(expirationTimeInMinutes)))
                .signWith(SignatureAlgorithm.HS512, authenticationProperties.getJwtSecretKey().getBytes())
                .compact();
    }

    private String getAuthority(final Authentication authentication) {
        return authentication.getAuthorities()
                .parallelStream()
                .map(item -> item.getAuthority())
                .findFirst()
                .orElse(null);
    }

    private Date createDateFromLocalDateTime(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
