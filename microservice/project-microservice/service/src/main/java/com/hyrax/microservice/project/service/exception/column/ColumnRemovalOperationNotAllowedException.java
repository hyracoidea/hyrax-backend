package com.hyrax.microservice.project.service.exception.column;

public class ColumnRemovalOperationNotAllowedException extends ColumnOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Column removal operation not allowed to %s";

    public ColumnRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
