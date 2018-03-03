package com.hyrax.microservice.project.service.exception.board;

public class BoardOperationNotAllowedException extends RuntimeException {

    public BoardOperationNotAllowedException(final String message) {
        super(message);
    }
}
