package com.hyrax.microservice.project.service.exception.team;

public class TeamAlreadyExistsException extends RuntimeException {

    public TeamAlreadyExistsException(final String message) {
        super(message);
    }
}
