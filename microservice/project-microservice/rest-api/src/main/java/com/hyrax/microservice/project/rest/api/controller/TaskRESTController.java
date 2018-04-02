package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.request.TaskCreationRequest;
import com.hyrax.microservice.project.rest.api.domain.request.TaskUpdateRequest;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.TaskResponse;
import com.hyrax.microservice.project.rest.api.domain.response.wrapper.TaskResponseWrapper;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.TaskService;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.task.TaskOperationNotAllowedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Api(description = "Operations about tasks")
@RestController
@AllArgsConstructor
public class TaskRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final TaskService taskService;

    private final ConversionService conversionService;

    @GetMapping(path = "/board/{boardName}/column/{columnName}/task")
    @ApiOperation(httpMethod = "GET", value = "Resource to list all the tasks on the given board and the given column")
    public ResponseEntity<TaskResponseWrapper> retrieveAll(@PathVariable final String boardName, @PathVariable final String columnName) {
        return ResponseEntity.ok()
                .body(TaskResponseWrapper.builder()
                        .taskResponses(taskService.findAllByBoardNameAndColumnName(boardName, columnName)
                                .stream()
                                .map(column -> conversionService.convert(column, TaskResponse.class))
                                .collect(Collectors.toList())
                        )
                        .build());
    }

    @PostMapping(path = "/board/{boardName}/column/{columnName}/task")
    @ApiOperation(httpMethod = "POST", value = "Resource to create a new task for the given column")
    public ResponseEntity<Void> create(@PathVariable final String boardName, @PathVariable final String columnName,
                                       @RequestBody final TaskCreationRequest taskCreationRequest) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received task creation request [boardName={} columnName={} taskName={} description={} requestedBy={}]",
                boardName, columnName, taskCreationRequest.getTaskName(), taskCreationRequest.getDescription(), requestedBy);

        taskService.create(boardName, columnName, taskCreationRequest.getTaskName(), taskCreationRequest.getDescription(), requestedBy);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/board/{boardName}/task/{taskId}/user/{username}")
    @ApiOperation(httpMethod = "PUT", value = "Resource to assign user to the given task")
    public ResponseEntity<Void> assignUserToTask(@PathVariable final String boardName, @PathVariable final Long taskId,
                                                 @PathVariable final String username) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received assign user to task request [boardName={} taskId={} username={} requestedBy={}]",
                boardName, taskId, username, requestedBy);

        taskService.assignUserToTask(boardName, taskId, username, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/board/{boardName}/column/{columnName}/task/{taskId}")
    @ApiOperation(httpMethod = "PUT", value = "Resource to update the name or the description of the existing task")
    public ResponseEntity<Void> update(@PathVariable final String boardName, @PathVariable final String columnName, @PathVariable final Long taskId,
                                       @RequestBody final TaskUpdateRequest taskUpdateRequest) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received task update request [boardName={} columnName={} taskId={} taskName={} description={} requestedBy={}]",
                boardName, columnName, taskId, taskUpdateRequest.getTaskName(), taskUpdateRequest.getDescription(), requestedBy);

        taskService.update(boardName, columnName, taskId, taskUpdateRequest.getTaskName(), taskUpdateRequest.getDescription(), requestedBy);

        return ResponseEntity.noContent().build();
    }

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

    @DeleteMapping(path = "/board/{boardName}/column/{columnName}/task/{taskId}")
    @ApiOperation(httpMethod = "DELETE", value = "Resource to remove the given task")
    public ResponseEntity<Void> delete(@PathVariable final String boardName, @PathVariable final String columnName, @PathVariable final Long taskId) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received task removal request [boardName={} columnName={} taskId={} requestedBy={}]", boardName, columnName, taskId, requestedBy);

        taskService.remove(boardName, columnName, taskId, requestedBy);

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
