package com.hyrax.microservice.project.service.exception;

public class TeamAlreadyExistsException extends RuntimeException {

    public TeamAlreadyExistsException(final String message) {
        super(message);
    }
}
