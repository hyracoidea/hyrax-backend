package com.hyrax.microservice.project.service.exception.board;

public class BoardRemovalOperationNotAllowedException extends BoardOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Board removal operation not allowed to %s";

    public BoardRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
