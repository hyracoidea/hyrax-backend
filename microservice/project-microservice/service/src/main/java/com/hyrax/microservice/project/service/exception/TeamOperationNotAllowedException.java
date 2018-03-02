package com.hyrax.microservice.project.service.exception;

public class TeamOperationNotAllowedException extends RuntimeException {

    public TeamOperationNotAllowedException(final String message) {
        super(message);
    }
}
