package com.hyrax.microservice.project.service.exception.board.member;

import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;

public class BoardMemberNotFoundException extends ResourceNotFoundException {

    private static final String BOARD_MEMBER_NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "Board member not found: %s";

    public BoardMemberNotFoundException(final String boardMemberUsername) {
        super(String.format(BOARD_MEMBER_NOT_FOUND_ERROR_MESSAGE_TEMPLATE, boardMemberUsername));
    }

}
