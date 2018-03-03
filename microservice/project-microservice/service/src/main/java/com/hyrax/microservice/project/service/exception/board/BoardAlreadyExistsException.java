package com.hyrax.microservice.project.service.exception.board;

public class BoardAlreadyExistsException extends RuntimeException {

    public BoardAlreadyExistsException(final String message) {
        super(message);
    }
}
