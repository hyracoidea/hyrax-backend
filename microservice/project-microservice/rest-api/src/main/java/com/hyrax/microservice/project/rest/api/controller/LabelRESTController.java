package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.request.LabelColorRequest;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.RequestValidationResponse;
import com.hyrax.microservice.project.rest.api.exception.RequestValidationException;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.rest.api.validation.bindingresult.BindingResultProcessor;
import com.hyrax.microservice.project.rest.api.validation.bindingresult.ProcessedBindingResult;
import com.hyrax.microservice.project.service.api.LabelService;
import com.hyrax.microservice.project.service.domain.LabelColor;
import com.hyrax.microservice.project.service.exception.label.LabelAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.label.LabelOperationNotAllowedException;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LabelRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final BindingResultProcessor bindingResultProcessor;

    private final LabelService labelService;

    private final ConversionService conversionService;

    @PostMapping(path = "/board/{boardName}/label/{labelName}")
    @ApiOperation(httpMethod = "POST", value = "Resource to create a new label for the given board")
    public ResponseEntity<Void> create(@PathVariable final String boardName, @PathVariable final String labelName,
                                       @Valid final LabelColorRequest labelColorRequest, final BindingResult bindingResult) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received label creation request [boardName={} labelName={} labelColorRequest={} requestedBy={}]", boardName, labelName, labelColorRequest, requestedBy);

        final ProcessedBindingResult processedBindingResult = bindingResultProcessor.process(bindingResult);

        if (!processedBindingResult.hasValidationErrors()) {
            labelService.create(boardName, labelName, conversionService.convert(labelColorRequest, LabelColor.class), requestedBy);
        } else {
            throw new RequestValidationException(processedBindingResult);
        }

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RequestValidationException.class)
    protected ResponseEntity<RequestValidationResponse> handleRequestValidationException(final RequestValidationException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(RequestValidationResponse.builder()
                        .requestValidationDetails(e.getProcessedBindingResult().getRequestValidationDetails())
                        .build()
                );
    }

    @ExceptionHandler(LabelAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponse> handleLabelAlreadyExistsException(final LabelAlreadyExistsException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(LabelOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleLabelOperationNotAllowedException(final LabelOperationNotAllowedException e) {
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
