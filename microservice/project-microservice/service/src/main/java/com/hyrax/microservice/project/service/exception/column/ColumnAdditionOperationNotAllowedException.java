package com.hyrax.microservice.project.service.exception.column;

public class ColumnAdditionOperationNotAllowedException extends ColumnOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Column addition operation not allowed to %s";

    public ColumnAdditionOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
