package com.hyrax.microservice.project.service.exception.column;

public class ColumnAlreadyExistsException extends RuntimeException {

    public ColumnAlreadyExistsException(final String message) {
        super(message);
    }
}
