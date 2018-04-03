package com.hyrax.microservice.project.service.exception.board.member;

public class BoardMemberRemovalOperationNotAllowedException extends BoardMemberOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Board member removal operation not allowed to %s";

    public BoardMemberRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
