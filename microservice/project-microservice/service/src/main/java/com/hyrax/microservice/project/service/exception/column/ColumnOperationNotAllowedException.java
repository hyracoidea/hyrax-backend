package com.hyrax.microservice.project.service.exception.column;

public class ColumnOperationNotAllowedException extends RuntimeException {

    public ColumnOperationNotAllowedException(final String message) {
        super(message);
    }
}
