package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.TaskService;
import com.hyrax.microservice.project.service.exception.task.WatchTaskException;
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
public class WatchTaskRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchTaskRESTController.class);

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final TaskService taskService;

    @PutMapping(path = "/board/{boardName}/task/{taskId}/watch")
    @ApiOperation(httpMethod = "PUT", value = "Resource to watch the given task")
    public ResponseEntity<Void> watchTask(@PathVariable final String boardName, @PathVariable final Long taskId) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received watch task request [boardName={} taskId={} requestedBy={}]", boardName, taskId, requestedBy);

        taskService.watchTask(boardName, taskId, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/board/{boardName}/task/{taskId}/unwatch")
    @ApiOperation(httpMethod = "PUT", value = "Resource to unwatch the given task")
    public ResponseEntity<Void> unwatchTask(@PathVariable final String boardName, @PathVariable final Long taskId) {

        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received unwatch task request [boardName={} taskId={} requestedBy={}]", boardName, taskId, requestedBy);

        taskService.unwatch(boardName, taskId, requestedBy);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(WatchTaskException.class)
    protected ResponseEntity<ErrorResponse> handleWatchTaskException(final WatchTaskException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }
}
