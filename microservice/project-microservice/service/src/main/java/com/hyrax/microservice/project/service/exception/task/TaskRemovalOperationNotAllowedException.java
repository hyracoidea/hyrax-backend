package com.hyrax.microservice.project.service.exception.task;

public class TaskRemovalOperationNotAllowedException extends TaskOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Task removal operation not allowed to %s";

    public TaskRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
