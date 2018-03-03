package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.BoardMemberService;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberIsAlreadyAddedException;
import com.hyrax.microservice.project.service.exception.board.member.BoardMemberOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BoardMemberRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardMemberRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final BoardMemberService boardMemberService;

    @PostMapping(path = "/board/{boardName}/member/{username}")
    public ResponseEntity<Void> addTeamMemberToTeam(@PathVariable final String boardName, @PathVariable final String username) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received board member addition request [boardName={} username={} requestedBy={}]", boardName, username, requestedBy);

        boardMemberService.add(username, boardName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BoardMemberIsAlreadyAddedException.class)
    protected ResponseEntity<ErrorResponse> handleBoardMemberIsAlreadyAddedException(final BoardMemberIsAlreadyAddedException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BoardMemberOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleBoardMemberOperationNotAllowedException(final BoardMemberOperationNotAllowedException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }
}