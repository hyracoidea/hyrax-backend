package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.BoardResponse;
import com.hyrax.microservice.project.rest.api.domain.response.wrapper.BoardResponseWrapper;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.BoardService;
import com.hyrax.microservice.project.service.exception.board.BoardAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.board.BoardOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/board")
public class BoardRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardRESTController.class);

    private final BoardService boardService;

    private final ConversionService conversionService;

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    @GetMapping
    public ResponseEntity<BoardResponseWrapper> retrieveAll() {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();

        return ResponseEntity.ok()
                .body(BoardResponseWrapper.builder()
                        .boardResponses(boardService.findAllByUsername(requestedBy)
                                .stream()
                                .map(board -> conversionService.convert(board, BoardResponse.class))
                                .collect(Collectors.toList())
                        )
                        .build());
    }

    @PostMapping(path = "/{boardName}")
    public ResponseEntity<Void> create(@PathVariable final String boardName) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received board creation request = [boardName={} requestedBy={}]", boardName, requestedBy);

        boardService.create(boardName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{boardName}")
    public ResponseEntity<Void> remove(@PathVariable final String boardName) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received board removal request = [boardName={} requestedBy={}]", boardName, requestedBy);

        boardService.remove(boardName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BoardAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponse> handleBoardAlreadyExistsException(final BoardAlreadyExistsException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BoardOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleBoardOperationNotAllowedException(final BoardOperationNotAllowedException e) {
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
