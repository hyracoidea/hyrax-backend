package com.hyrax.spring.boot.starter.authentication.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyrax.spring.boot.starter.authentication.jwt.JWTTokenGenerator;
import com.hyrax.spring.boot.starter.authentication.model.SecuredAccountDetails;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationProperties authenticationProperties;
    private final JWTTokenGenerator jwtTokenGenerator;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(final AuthenticationManager authenticationManager, final AuthenticationProperties authenticationProperties,
                                   final JWTTokenGenerator jwtTokenGenerator, final ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.authenticationProperties = authenticationProperties;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        try {
            SecuredAccountDetails accountDetails = objectMapper.readValue(request.getInputStream(), SecuredAccountDetails.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(accountDetails.getUsername(), accountDetails.getPassword(), Collections.emptyList()));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authentication) {
        final String token = jwtTokenGenerator.generateToken(authentication);
        response.addHeader(authenticationProperties.getHeaderName(), authenticationProperties.getJwtTokenPrefix() + token);
    }
}