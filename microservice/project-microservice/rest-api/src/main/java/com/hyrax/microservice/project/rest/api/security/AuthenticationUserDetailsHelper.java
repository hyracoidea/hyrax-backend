package com.hyrax.microservice.project.rest.api.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUserDetailsHelper {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
