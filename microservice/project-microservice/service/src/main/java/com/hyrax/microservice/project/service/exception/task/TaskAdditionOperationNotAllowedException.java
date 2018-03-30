package com.hyrax.microservice.project.service.exception.task;

public class TaskAdditionOperationNotAllowedException extends TaskOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Task addition operation not allowed to %s";

    public TaskAdditionOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
