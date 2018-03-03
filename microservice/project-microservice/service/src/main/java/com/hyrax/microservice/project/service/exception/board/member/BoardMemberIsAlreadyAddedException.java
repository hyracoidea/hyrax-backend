package com.hyrax.microservice.project.service.exception.board.member;

public class BoardMemberIsAlreadyAddedException extends RuntimeException {

    public BoardMemberIsAlreadyAddedException(final String message) {
        super(message);
    }

}
