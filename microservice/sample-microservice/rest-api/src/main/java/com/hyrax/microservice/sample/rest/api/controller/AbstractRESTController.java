package com.hyrax.microservice.sample.rest.api.controller;

import com.hyrax.client.common.api.response.ErrorResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;

abstract class AbstractRESTController {

    private final Logger logger;

    AbstractRESTController(final Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleBadRequest(final Exception e) {
        logException(e);
        return createErrorResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleInternalServerError(final Exception e) {
        logException(e);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    protected void logException(final Exception e) {
        logger.error(e.getMessage(), e);
    }

    protected ResponseEntity<ErrorResponse> createErrorResponse(final HttpStatus httpStatus, final Exception e) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .exceptionMessage(e.getMessage())
                        .exceptionType(e.getClass().getTypeName())
                        .httpStatus(httpStatus.getReasonPhrase())
                        .httpStatusCode(httpStatus.value())
                        .time(ZonedDateTime.now())
                        .build()
                );
    }

}
