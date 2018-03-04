package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.ColumnService;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.column.ColumnAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.column.ColumnOperationNotAllowedException;
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
public class ColumnRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColumnRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final ColumnService columnService;

    @PostMapping(path = "/board/{boardName}/column/{columnName}")
    public ResponseEntity<Void> create(@PathVariable final String boardName, @PathVariable final String columnName) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received column creation request [boardName={} columnName={} requestedBy={}]", boardName, columnName, requestedBy);

        columnService.create(boardName, columnName, requestedBy);

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

    @ExceptionHandler(ColumnAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponse> handleColumnAlreadyExistsException(final ColumnAlreadyExistsException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ColumnOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleColumnOperationNotAllowedException(final ColumnOperationNotAllowedException e) {
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