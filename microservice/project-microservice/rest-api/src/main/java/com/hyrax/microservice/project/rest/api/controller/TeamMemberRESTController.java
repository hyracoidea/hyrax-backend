package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.request.TeamMemberAdditionRequest;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.TeamMemberUsernameWrapperResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.TeamMemberService;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.TeamMemberIsAlreadyAddedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamMemberRESTController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMemberRESTController.class);

    private final TeamMemberService teamMemberService;

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    @Autowired
    public TeamMemberRESTController(final TeamMemberService teamMemberService, final AuthenticationUserDetailsHelper authenticationUserDetailsHelper) {
        this.teamMemberService = teamMemberService;
        this.authenticationUserDetailsHelper = authenticationUserDetailsHelper;
    }

    @GetMapping(path = "/team/{teamName}/members")
    public ResponseEntity<TeamMemberUsernameWrapperResponse> retrieveTeamMemberUsernames(@PathVariable final String teamName) {
        return ResponseEntity.ok(TeamMemberUsernameWrapperResponse.builder()
                .teamMemberUsernames(teamMemberService.findAllUsernameByTeamName(teamName))
                .build()
        );
    }

    @PostMapping(path = "/team/member")
    public ResponseEntity<Void> addTeamMemberToTeam(@RequestBody final TeamMemberAdditionRequest request) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received team member addition request: {} from {}", request, requestedBy);

        teamMemberService.add(request.getUsername(), request.getTeamName(), requestedBy);

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

    @ExceptionHandler(TeamMemberIsAlreadyAddedException.class)
    protected ResponseEntity<ErrorResponse> handleTeamMemberAlreadyAddedException(final TeamMemberIsAlreadyAddedException e) {
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
