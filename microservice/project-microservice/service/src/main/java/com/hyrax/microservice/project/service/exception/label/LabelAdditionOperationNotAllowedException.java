package com.hyrax.microservice.project.service.exception.label;

public class LabelAdditionOperationNotAllowedException extends LabelOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Label addition operation not allowed to %s";

    public LabelAdditionOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
