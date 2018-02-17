package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.request.TeamCreationRequest;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.RequestValidationResponse;
import com.hyrax.microservice.project.rest.api.exception.RequestValidationException;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.rest.api.validation.bindingresult.BindingResultProcessor;
import com.hyrax.microservice.project.rest.api.validation.bindingresult.ProcessedBindingResult;
import com.hyrax.microservice.project.service.api.TeamService;
import com.hyrax.microservice.project.service.domain.Team;
import com.hyrax.microservice.project.service.exception.TeamAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/team")
public class TeamRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamRESTController.class);

    private final TeamService teamService;

    private final ConversionService conversionService;

    private final BindingResultProcessor bindingResultProcessor;

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    @Autowired
    public TeamRESTController(final TeamService teamService, final ConversionService conversionService, final BindingResultProcessor bindingResultProcessor,
                              final AuthenticationUserDetailsHelper authenticationUserDetailsHelper) {
        this.teamService = teamService;
        this.conversionService = conversionService;
        this.bindingResultProcessor = bindingResultProcessor;
        this.authenticationUserDetailsHelper = authenticationUserDetailsHelper;
    }

    @PostMapping
    public ResponseEntity<Void> createTeam(@Valid @RequestBody final TeamCreationRequest teamCreationRequest, final BindingResult bindingResult) {
        LOGGER.info("Received team creation request={} from {}", teamCreationRequest, authenticationUserDetailsHelper.getUsername());

        final ProcessedBindingResult processedBindingResult = bindingResultProcessor.process(bindingResult);

        if (!processedBindingResult.hasValidationErrors()) {
            teamService.save(conversionService.convert(teamCreationRequest, Team.class));
        } else {
            throw new RequestValidationException(processedBindingResult);
        }

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

    @ExceptionHandler(TeamAlreadyExistsException.class)
    protected ResponseEntity<ErrorResponse> handleTeamAlreadyExistsException(final TeamAlreadyExistsException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build()
                );
    }

    private void logException(final Exception e) {
        LOGGER.error(e.getMessage(), e);
    }
}
