package com.hyrax.microservice.project.service.exception.team;

import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;

public class TeamNotFoundException extends ResourceNotFoundException {

    private static final String TEAM_NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "Team not found: %s";

    public TeamNotFoundException(final String teamName) {
        super(String.format(TEAM_NOT_FOUND_ERROR_MESSAGE_TEMPLATE, teamName));
    }
}
