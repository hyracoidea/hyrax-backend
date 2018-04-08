package com.hyrax.microservice.email.rest.api.controller;

import com.hyrax.microservice.email.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.email.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.email.service.exception.UpdateOperationNotAllowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

abstract class AbstractRESTController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(UpdateOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleUpdateOperationNotAllowedException(final UpdateOperationNotAllowedException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    private void logException(final Exception e) {
        logger.error(e.getMessage(), e);
    }
}
