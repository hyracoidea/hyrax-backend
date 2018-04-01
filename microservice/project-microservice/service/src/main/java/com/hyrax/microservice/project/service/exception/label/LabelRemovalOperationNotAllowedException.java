package com.hyrax.microservice.project.service.exception.label;

public class LabelRemovalOperationNotAllowedException extends LabelOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Label removal operation not allowed to %s";

    public LabelRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
