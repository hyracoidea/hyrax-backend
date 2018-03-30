package com.hyrax.microservice.project.service.exception.task;

public class TaskUpdateOperationNotAllowedException extends TaskOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Task update operation not allowed to %s";

    public TaskUpdateOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
