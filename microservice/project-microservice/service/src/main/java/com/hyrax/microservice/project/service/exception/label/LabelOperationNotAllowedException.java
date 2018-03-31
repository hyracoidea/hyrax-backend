package com.hyrax.microservice.project.service.exception.label;

public class LabelOperationNotAllowedException extends RuntimeException {

    public LabelOperationNotAllowedException(final String message) {
        super(message);
    }
}
