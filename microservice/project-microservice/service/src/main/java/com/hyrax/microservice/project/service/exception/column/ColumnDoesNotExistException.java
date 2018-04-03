package com.hyrax.microservice.project.service.exception.column;

public class ColumnDoesNotExistException extends RuntimeException {

    public ColumnDoesNotExistException(final String message) {
        super(message);
    }
}
