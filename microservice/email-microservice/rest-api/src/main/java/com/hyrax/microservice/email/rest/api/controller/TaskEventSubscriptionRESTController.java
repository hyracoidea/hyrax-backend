package com.hyrax.microservice.email.rest.api.controller;

import com.hyrax.client.email.api.request.TaskEventSubscriptionRequest;
import com.hyrax.client.email.api.response.TaskEventSubscriptionResponse;
import com.hyrax.microservice.email.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.email.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.email.service.api.TaskEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;
import com.hyrax.microservice.email.service.exception.UpdateOperationNotAllowedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email/settings/task")
@Api(description = "Operations about task event subscriptions")
@AllArgsConstructor
public class TaskEventSubscriptionRESTController extends AbstractRESTController {

    private static final String ERROR_MESSAGE = "Task event subscription settings are not found";

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final TaskEventSubscriptionService taskEventSubscriptionService;

    private final ConversionService conversionService;

    private final ModelMapper modelMapper;

    @GetMapping
    @ApiOperation(httpMethod = "GET", value = "Resource to list the task event subscription settings for the given user")
    public ResponseEntity<TaskEventSubscriptionResponse> retrieveTaskEventSubscriptionSettings() {
        final String username = authenticationUserDetailsHelper.getUsername();
        final TaskEventSubscription taskEventSubscription = taskEventSubscriptionService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE));
        return ResponseEntity.ok(conversionService.convert(taskEventSubscription, TaskEventSubscriptionResponse.class));
    }

    @PutMapping
    @ApiOperation(httpMethod = "PUT", value = "Resource to modify the task event subscription settings for the given user")
    public ResponseEntity<Void> saveOrUpdateTaskEventSubscriptionSettings(@RequestBody final TaskEventSubscriptionRequest taskEventSubscriptionRequest) {
        logger.info("Received task event subscription settings to update : {}", taskEventSubscriptionRequest);
        if (authenticationUserDetailsHelper.getUsername().equals(taskEventSubscriptionRequest.getUsername())) {
            taskEventSubscriptionService.saveOrUpdate(modelMapper.map(taskEventSubscriptionRequest, TaskEventSubscription.class));
        } else {
            throw new UpdateOperationNotAllowedException(authenticationUserDetailsHelper.getUsername());
        }
        return ResponseEntity.noContent().build();
    }

}
