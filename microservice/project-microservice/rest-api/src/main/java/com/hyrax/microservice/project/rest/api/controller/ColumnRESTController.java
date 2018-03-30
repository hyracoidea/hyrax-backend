package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ColumnResponse;
import com.hyrax.microservice.project.rest.api.domain.response.wrapper.ColumnResponseWrapper;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.ColumnService;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.column.ColumnAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.column.ColumnOperationNotAllowedException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ColumnRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColumnRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final ColumnService columnService;

    private final ConversionService conversionService;

    @GetMapping(path = "/board/{boardName}/column")
    public ResponseEntity<ColumnResponseWrapper> retrieveAll(@PathVariable final String boardName) {
        return ResponseEntity.ok()
                .body(ColumnResponseWrapper.builder()
                        .columnResponses(columnService.findAllByBoardName(boardName)
                                .stream()
                                .map(column -> conversionService.convert(column, ColumnResponse.class))
                                .collect(Collectors.toList())
                        )
                        .build());
    }

    @PostMapping(path = "/board/{boardName}/column/{columnName}")
    public ResponseEntity<Void> create(@PathVariable final String boardName, @PathVariable final String columnName) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received column creation request [boardName={} columnName={} requestedBy={}]", boardName, columnName, requestedBy);

        columnService.create(boardName, columnName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/board/{boardName}/column/{columnName}/order")
    public ResponseEntity<Void> updateColumnIndex(@PathVariable final String boardName, @PathVariable final String columnName,
                                                  @RequestParam final long from, @RequestParam final long to) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received column order updating request [boardName={} columnName={} from={} to={} requestedBy={}]",
                boardName, columnName, from, to, requestedBy);

        columnService.updateIndex(boardName, columnName, requestedBy, from, to);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/board/{boardName}/column/{columnName}")
    public ResponseEntity<Void> delete(@PathVariable final String boardName, @PathVariable final String columnName) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received column removal request [boardName={} columnName={} requestedBy={}]", boardName, columnName, requestedBy);

        columnService.remove(boardName, columnName, requestedBy);

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
