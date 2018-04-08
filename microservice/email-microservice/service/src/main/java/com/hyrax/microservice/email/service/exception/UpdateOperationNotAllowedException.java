package com.hyrax.microservice.email.service.exception;

public class UpdateOperationNotAllowedException extends RuntimeException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Update operation not allowed to %s";

    public UpdateOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }

}
