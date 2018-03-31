package com.hyrax.microservice.project.rest.api.controller;

import com.hyrax.microservice.project.rest.api.domain.request.TeamMemberAdditionRequest;
import com.hyrax.microservice.project.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.project.rest.api.domain.response.wrapper.TeamMemberUsernameWrapperResponse;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.api.TeamMemberService;
import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;
import com.hyrax.microservice.project.service.exception.team.member.TeamMemberIsAlreadyAddedException;
import com.hyrax.microservice.project.service.exception.team.member.TeamMemberOperationNotAllowedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "Operations about tasks")
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
    @ApiOperation(httpMethod = "GET", value = "Resource to list all team members for the given team")
    public ResponseEntity<TeamMemberUsernameWrapperResponse> retrieveTeamMemberUsernames(@PathVariable final String teamName) {
        return ResponseEntity.ok(TeamMemberUsernameWrapperResponse.builder()
                .teamMemberUsernames(teamMemberService.findAllUsernameByTeamName(teamName))
                .build()
        );
    }

    @PostMapping(path = "/team/member")
    @ApiOperation(httpMethod = "POST", value = "Resource to add a new member to the given team")
    public ResponseEntity<Void> addTeamMemberToTeam(@RequestBody final TeamMemberAdditionRequest request) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received team member addition request: {} from {}", request, requestedBy);

        teamMemberService.add(request.getUsername(), request.getTeamName(), requestedBy);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/team/{teamName}/member/{username}")
    @ApiOperation(httpMethod = "DELETE", value = "Resource to remove a team member from the given team")
    public ResponseEntity<Void> removeTeamMember(@PathVariable final String teamName, @PathVariable final String username) {
        final String requestedBy = authenticationUserDetailsHelper.getUsername();
        LOGGER.info("Received team member deletion request=[username={} teamName={} requestedBy={}]", username, teamName, requestedBy);

        teamMemberService.remove(username, teamName, requestedBy);

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

    @ExceptionHandler(TeamMemberOperationNotAllowedException.class)
    protected ResponseEntity<ErrorResponse> handleTeamMemberOperationNotAllowedException(final TeamMemberOperationNotAllowedException e) {
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
