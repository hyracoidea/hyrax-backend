package com.hyrax.microservice.project.service.exception;

public class BoardOperationNotAllowedException extends RuntimeException {

    public BoardOperationNotAllowedException(final String message) {
        super(message);
    }
}
