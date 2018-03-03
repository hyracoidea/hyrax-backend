package com.hyrax.microservice.project.service.exception.board.member;

public class BoardMemberAdditionOperationNotAllowedException extends BoardMemberOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Board member addition operation not allowed to %s";

    public BoardMemberAdditionOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
