package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.request.LabelColorRequest;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.LabelResponse;
import com.hyrax.microservice.project.rest.api.domain.response.RequestValidationResponse;
import com.hyrax.microservice.project.rest.api.domain.response.wrapper.LabelResponseWrapper;
import com.hyrax.microservice.project.rest.api.exception.RequestValidationException;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.rest.api.validation.bindingresult.BindingResultProcessor;
import com.hyrax.microservice.project.rest.api.validation.bindingresult.ProcessedBindingResult;
import com.hyrax.microservice.project.service.api.LabelService;
import com.hyrax.microservice.project.service.exception.label.LabelAdditionToTaskException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class LabelRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final BindingResultProcessor bindingResultProcessor;

    private final LabelService labelService;

    private final ConversionService conversionService;

    @GetMapping(path = "/board/{boardName}/label")
    @ApiOperation(httpMethod = "GET", value = "Resource to list all the labels by the given board name")
    public ResponseEntity<LabelResponseWrapper> retrieveAll(@PathVariable final String boardName) {
        return ResponseEntity.ok()
                .body(LabelResponseWrapper.builder()
                        .labelResponses(labelService.findAllByBoardName(boardName)
                                .stream()
                                .map(label -> conversionService.convert(label, LabelResponse.class))
                                .collect(Collectors.toList())
                        )
                        .build());
    }

    @PostMapping(path = "/board/{boardName}/label/{labelName}")
    @ApiOperation(httpMethod = "POST", value = "Resource to create a new label for the given board")
    public ResponseEntity<Void> create(@PathVariable final String boardName, @PathVariable final String labelName,
                                       @Valid final LabelColorRequest labelColorRequest, final BindingResult bindingResult) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received label creation request [boardName={} labelName={} labelColorRequest={} requestedBy={}]", boardName, labelName, labelColorRequest, requestedBy);

        final ProcessedBindingResult processedBindingResult = bindingResultProcessor.process(bindingResult);

        if (!processedBindingResult.hasValidationErrors()) {
            labelService.create(boardName, labelName, conversionService.convert(labelColorRequest, com.hyrax.microservice.project.service.domain.LabelColor.class), requestedBy);
        } else {
            throw new RequestValidationException(processedBindingResult);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/board/{boardName}/label/{labelId}/task/{taskId}")
    @ApiOperation(httpMethod = "POST", value = "Resource to add the given label for the given task")
    public ResponseEntity<Void> addLabelToTask(@PathVariable final String boardName, @PathVariable final Long taskId, @PathVariable final Long labelId) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received label addition to task request [boardName={} taskId={} labelId={} requestedBy={}]", boardName, taskId, labelId, requestedBy);

        labelService.addLabelToTask(boardName, taskId, labelId, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/board/{boardName}/label/{labelId}")
    @ApiOperation(httpMethod = "DELETE", value = "Resource to remove the given label")
    public ResponseEntity<Void> delete(@PathVariable final String boardName, @PathVariable final Long labelId) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received label removal request [boardName={} labelId={} requestedBy={}]", boardName, labelId, requestedBy);

        labelService.remove(boardName, labelId, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/board/{boardName}/label/{labelId}/task/{taskId}")
    @ApiOperation(httpMethod = "DELETE", value = "Resource to remove the given label from the task")
    public ResponseEntity<Void> deleteLabelFromTask(@PathVariable final String boardName, @PathVariable final Long taskId, @PathVariable final Long labelId) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received label removal from task request [boardName={} taskId={} labelId={} requestedBy={}]", boardName, taskId, labelId, requestedBy);

        labelService.removeLabelFromTask(boardName, taskId, labelId, requestedBy);

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

    @ExceptionHandler(LabelAdditionToTaskException.class)
    protected ResponseEntity<ErrorResponse> handleLabelAdditionToTaskException(final LabelAdditionToTaskException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
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
