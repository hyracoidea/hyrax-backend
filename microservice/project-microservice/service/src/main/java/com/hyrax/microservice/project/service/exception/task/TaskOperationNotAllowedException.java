package com.hyrax.microservice.project.service.exception.task;

public class TaskOperationNotAllowedException extends RuntimeException {

    public TaskOperationNotAllowedException(final String message) {
        super(message);
    }
}
