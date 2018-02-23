package com.hyrax.microservice.project.service.exception;

public class UsernameNotFoundException extends ResourceNotFoundException {

    private static final String USERNAME_NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "Username not found: %s";

    public UsernameNotFoundException(final String username) {
        super(String.format(USERNAME_NOT_FOUND_ERROR_MESSAGE_TEMPLATE, username));
    }
}
