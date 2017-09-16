package com.hyrax.microservice.sample.rest.api.controller;

import com.hyrax.microservice.sample.rest.api.response.EchoResponse;
import com.hyrax.microservice.sample.rest.api.response.ErrorResponse;
import com.hyrax.microservice.sample.service.api.EchoService;
import com.hyrax.microservice.sample.service.domain.Echo;
import com.hyrax.microservice.sample.service.exception.EchoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class EchoRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EchoRESTController.class);

    private final EchoService echoService;
    private final ConversionService conversionService;

    @Autowired
    public EchoRESTController(final EchoService echoService, final ConversionService conversionService) {
        this.echoService = echoService;
        this.conversionService = conversionService;
    }

    @GetMapping(path = "/echo/{echoId}")
    public ResponseEntity<EchoResponse> retrieveEchoResponse(@PathVariable(name = "echoId") final Long echoId) {
        LOGGER.info("Received echoId is: {}", echoId);

        final Echo echo = echoService.getByEchoId(echoId).orElseThrow(() -> new EchoNotFoundException("Echo not found with id: " + String.valueOf(echoId)));
        return ResponseEntity.ok(conversionService.convert(echo, EchoResponse.class));
    }

    @ExceptionHandler({IllegalArgumentException.class, EchoNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleResourceNotFoundException(final Exception e) {
        logException(e);
        return createErrorResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleGeneralServerException(final Exception e) {
        logException(e);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(final HttpStatus httpStatus, final Exception e) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .errorMessage(e.getMessage())
                        .exceptionType(e.getClass().getTypeName())
                        .time(ZonedDateTime.now())
                        .build()
                );
    }
}
