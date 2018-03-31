package com.hyrax.microservice.project.service.exception.label;

public class LabelAlreadyExistsException extends RuntimeException {

    public LabelAlreadyExistsException(final String message) {
        super(message);
    }
}
