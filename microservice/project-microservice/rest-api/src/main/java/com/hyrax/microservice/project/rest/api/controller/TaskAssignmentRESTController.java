package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.TaskAssignmentService;
import com.hyrax.microservice.project.service.exception.task.AssignUserToTaskException;
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
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Operations about tasks")
@RestController
@AllArgsConstructor
public class TaskAssignmentRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskAssignmentRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final TaskAssignmentService taskAssignmentService;

    @PutMapping(path = "/board/{boardName}/task/{taskId}/user/{username}")
    @ApiOperation(httpMethod = "PUT", value = "Resource to assign user to the given task")
    public ResponseEntity<Void> assignUserToTask(@PathVariable final String boardName, @PathVariable final Long taskId,
                                                 @PathVariable final String username) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received assign user to task request [boardName={} taskId={} username={} requestedBy={}]",
                boardName, taskId, username, requestedBy);

        taskAssignmentService.assignUserToTask(boardName, taskId, username, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(AssignUserToTaskException.class)
    protected ResponseEntity<ErrorResponse> handleAssignUserToTaskException(final AssignUserToTaskException e) {
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
