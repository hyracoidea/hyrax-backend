package com.hyrax.microservice.email.rest.api.controller;

import com.hyrax.client.email.api.request.TeamEventSubscriptionRequest;
import com.hyrax.client.email.api.response.TeamEventSubscriptionResponse;
import com.hyrax.microservice.email.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.email.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.email.service.api.TeamEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;
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
@RequestMapping(path = "/email/settings/team")
@Api(description = "Operations about team event subscriptions")
@AllArgsConstructor
public class TeamEventSubscriptionRESTController extends AbstractRESTController {

    private static final String ERROR_MESSAGE = "Team event subscription settings are not found";

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final TeamEventSubscriptionService teamEventSubscriptionService;

    private final ConversionService conversionService;

    private final ModelMapper modelMapper;

    @GetMapping
    @ApiOperation(httpMethod = "GET", value = "Resource to list the team event subscription settings for the given user")
    public ResponseEntity<TeamEventSubscriptionResponse> retrieveTeamEventSubscriptionSettings() {
        final String username = authenticationUserDetailsHelper.getUsername();
        final TeamEventSubscription teamEventSubscription = teamEventSubscriptionService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE));
        return ResponseEntity.ok(conversionService.convert(teamEventSubscription, TeamEventSubscriptionResponse.class));
    }

    @PutMapping
    @ApiOperation(httpMethod = "PUT", value = "Resource to modify the team event subscription settings for the given user")
    public ResponseEntity<Void> saveOrUpdateTeamEventSubscriptionSettings(@RequestBody final TeamEventSubscriptionRequest teamEventSubscriptionRequest) {
        logger.info("Received team event subscription settings to update : {}", teamEventSubscriptionRequest);
        teamEventSubscriptionService.saveOrUpdate(modelMapper.map(teamEventSubscriptionRequest, TeamEventSubscription.class));
        return ResponseEntity.noContent().build();
    }

}
