package com.hyrax.microservice.email.rest.api.controller;

import com.hyrax.client.email.api.request.BoardEventSubscriptionRequest;
import com.hyrax.client.email.api.response.BoardEventSubscriptionResponse;
import com.hyrax.microservice.email.rest.api.exception.ResourceNotFoundException;
import com.hyrax.microservice.email.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.email.service.api.BoardEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;
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
@RequestMapping(path = "/email/settings/board")
@Api(description = "Operations about board event subscriptions")
@AllArgsConstructor
public class BoardEventSubscriptionRESTController extends AbstractRESTController {

    private static final String ERROR_MESSAGE = "Board event subscription settings are not found";

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    private final BoardEventSubscriptionService boardEventSubscriptionService;

    private final ConversionService conversionService;

    private final ModelMapper modelMapper;

    @GetMapping
    @ApiOperation(httpMethod = "GET", value = "Resource to list the board event subscription settings for the given user")
    public ResponseEntity<BoardEventSubscriptionResponse> retrieveBoardEventSubscriptionSettings() {
        final String username = authenticationUserDetailsHelper.getUsername();
        final BoardEventSubscription boardEventSubscription = boardEventSubscriptionService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ERROR_MESSAGE));
        return ResponseEntity.ok(conversionService.convert(boardEventSubscription, BoardEventSubscriptionResponse.class));
    }

    @PutMapping
    @ApiOperation(httpMethod = "PUT", value = "Resource to modify the board event subscription settings for the given user")
    public ResponseEntity<Void> saveOrUpdateBoardEventSubscriptionSettings(@RequestBody final BoardEventSubscriptionRequest boardEventSubscriptionRequest) {
        logger.info("Received board event subscription settings to update : {}", boardEventSubscriptionRequest);
        boardEventSubscriptionService.saveOrUpdate(modelMapper.map(boardEventSubscriptionRequest, BoardEventSubscription.class));
        return ResponseEntity.noContent().build();
    }

}
