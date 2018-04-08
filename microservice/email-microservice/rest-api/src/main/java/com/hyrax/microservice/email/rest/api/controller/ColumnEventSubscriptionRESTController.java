package com.hyrax.microservice.email.rest.api.controller;

import com.hyrax.client.email.api.request.ColumnEventSubscriptionRequest;
import com.hyrax.client.email.api.response.ColumnEventSubscriptionResponse;
import com.hyrax.microservice.email.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.email.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.email.service.api.ColumnEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;
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
@RequestMapping(path = "/email/settings/column")
@Api(description = "Operations about column event subscriptions")
@AllArgsConstructor
public class ColumnEventSubscriptionRESTController extends AbstractRESTController {

    private static final String ERROR_MESSAGE = "Column event subscription settings are not found";

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final ColumnEventSubscriptionService columnEventSubscriptionService;

    private final ConversionService conversionService;

    private final ModelMapper modelMapper;

    @GetMapping
    @ApiOperation(httpMethod = "GET", value = "Resource to list the column event subscription settings for the given user")
    public ResponseEntity<ColumnEventSubscriptionResponse> retrieveColumnEventSubscriptionSettings() {
        final String username = authenticationUserDetailsHelper.getUsername();
        final ColumnEventSubscription columnEventSubscription = columnEventSubscriptionService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE));
        return ResponseEntity.ok(conversionService.convert(columnEventSubscription, ColumnEventSubscriptionResponse.class));
    }

    @PutMapping
    @ApiOperation(httpMethod = "PUT", value = "Resource to modify the column event subscription settings for the given user")
    public ResponseEntity<Void> saveOrUpdateColumnEventSubscriptionSettings(@RequestBody final ColumnEventSubscriptionRequest columnEventSubscriptionRequest) {
        logger.info("Received column event subscription settings to update : {}", columnEventSubscriptionRequest);
        if (authenticationUserDetailsHelper.getUsername().equals(columnEventSubscriptionRequest.getUsername())) {
            columnEventSubscriptionService.saveOrUpdate(modelMapper.map(columnEventSubscriptionRequest, ColumnEventSubscription.class));
        } else {
            throw new UpdateOperationNotAllowedException(authenticationUserDetailsHelper.getUsername());
        }
        return ResponseEntity.noContent().build();
    }

}
