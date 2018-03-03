package com.hyrax.microservice.project.service.exception.board.member;

public class BoardMemberOperationNotAllowedException extends RuntimeException {

    public BoardMemberOperationNotAllowedException(final String message) {
        super(message);
    }
}
