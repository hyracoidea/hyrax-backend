package com.hyrax.spring.boot.starter.authentication.jwt.filter;

import com.hyrax.spring.boot.starter.authentication.jwt.JWTTokenParser;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private static final String AUTHORITY_KEY = "authority";

    private final JWTTokenParser jwtTokenParser;
    private final AuthenticationProperties authenticationProperties;

    public JWTAuthorizationFilter(final AuthenticationManager authManager, final AuthenticationProperties authenticationProperties, final JWTTokenParser jwtTokenParser) {
        super(authManager);
        this.authenticationProperties = authenticationProperties;
        this.jwtTokenParser = jwtTokenParser;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        final String token = request.getHeader(authenticationProperties.getHeaderName());
        if (Objects.isNull(token) || !token.startsWith(authenticationProperties.getJwtTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final String token) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if (Objects.nonNull(token)) {
            try {
                usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationTokenFrom(jwtTokenParser.parse(token));
            } catch (final JwtException e) {
                LOGGER.error("JWT exception occurred", e);
            }
        }
        return usernamePasswordAuthenticationToken;
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationTokenFrom(final Claims claims) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;

        final String user = claims.getSubject();
        final String authority = claims.get(AUTHORITY_KEY, String.class);

        if (user != null) {
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(() -> authority));
        }
        return usernamePasswordAuthenticationToken;
    }
}