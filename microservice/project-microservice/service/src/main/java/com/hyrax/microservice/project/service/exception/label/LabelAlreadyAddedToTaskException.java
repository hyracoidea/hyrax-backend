package com.hyrax.microservice.project.service.exception.label;

public class LabelAlreadyAddedToTaskException extends RuntimeException {

    public LabelAlreadyAddedToTaskException(final String message) {
        super(message);
    }
}
