package com.hyrax.microservice.project.service.exception.column;

public class ColumnUpdateOperationNotAllowedException extends ColumnOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Column update operation not allowed to %s";

    public ColumnUpdateOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
