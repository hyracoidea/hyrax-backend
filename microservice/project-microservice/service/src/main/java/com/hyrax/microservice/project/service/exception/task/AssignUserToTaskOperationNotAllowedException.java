package com.hyrax.microservice.project.service.exception.task;

public class AssignUserToTaskOperationNotAllowedException extends TaskOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Assign user to task operation not allowed to %s";

    public AssignUserToTaskOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
