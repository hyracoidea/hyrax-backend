package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.TaskService;
import com.hyrax.microservice.project.service.exception.column.ColumnDoesNotExistException;
import com.hyrax.microservice.project.service.exception.task.TaskOperationNotAllowedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Operations about tasks")
@RestController
@AllArgsConstructor
public class MovingTaskRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingTaskRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final TaskService taskService;

    @PutMapping(path = "/board/{boardName}/column/{columnName}/task/{taskId}/order")
    @ApiOperation(httpMethod = "PUT", value = "Resource to update the position of the task in the given column")
    public ResponseEntity<Void> updateTaskIndex(@PathVariable final String boardName, @PathVariable final String columnName, @PathVariable final Long taskId,
                                                @RequestParam final long from, @RequestParam final long to) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received task order update request [boardName={} columnName={} taskId={} from={} to={} requestedBy={}]",
                boardName, columnName, taskId, from, to, requestedBy);

        taskService.updatePosition(boardName, columnName, taskId, from, to, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/board/{boardName}/column/{columnName}/task/{taskId}/newcolumn/{newColumnName}")
    @ApiOperation(httpMethod = "PUT", value = "Resource to move the task between columns")
    public ResponseEntity<Void> updateTaskColumnId(@PathVariable final String boardName, @PathVariable final String columnName, @PathVariable final Long taskId,
                                                   @PathVariable final String newColumnName) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received task moving update request [boardName={} columnName={} taskId={} newColumnName={} requestedBy={}]",
                boardName, columnName, taskId, newColumnName, requestedBy);

        taskService.updatePositionBetweenColumns(boardName, columnName, taskId, newColumnName, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ColumnDoesNotExistException.class)
    protected ResponseEntity<ErrorResponse> handleColumnDoesNotExistException(final ColumnDoesNotExistException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(TaskOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleTaskOperationNotAllowedException(final TaskOperationNotAllowedException e) {
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
