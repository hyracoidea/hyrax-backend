package com.hyrax.microservice.project.service.exception.board;

import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;

public class BoardNotFoundException extends ResourceNotFoundException {

    private static final String BOARD_NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "Board not found: %s";

    public BoardNotFoundException(final String boardName) {
        super(String.format(BOARD_NOT_FOUND_ERROR_MESSAGE_TEMPLATE, boardName));
    }
}
