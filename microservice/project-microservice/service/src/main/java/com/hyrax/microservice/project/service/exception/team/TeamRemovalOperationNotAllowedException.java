package com.hyrax.microservice.project.service.exception.team;

public class TeamRemovalOperationNotAllowedException extends TeamOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Team removal operation not allowed to %s";

    public TeamRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
