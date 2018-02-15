package com.hyrax.spring.boot.starter.authentication.jwt;

import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenParser {

    private AuthenticationProperties authenticationProperties;

    @Autowired
    public JWTTokenParser(final AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    public Claims parse(final String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(authenticationProperties.getJwtSecretKey().getBytes())
                .parseClaimsJws(token.replace(authenticationProperties.getJwtTokenPrefix(), ""))
                .getBody();
    }
}
