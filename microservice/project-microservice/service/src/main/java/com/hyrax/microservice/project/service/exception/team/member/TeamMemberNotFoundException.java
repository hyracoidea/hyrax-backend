package com.hyrax.microservice.project.service.exception.team.member;

import com.hyrax.microservice.project.service.exception.ResourceNotFoundException;

public class TeamMemberNotFoundException extends ResourceNotFoundException {

    private static final String TEAM_MEMBER_NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "Team member not found: %s";

    public TeamMemberNotFoundException(final String teamMemberUsername) {
        super(String.format(TEAM_MEMBER_NOT_FOUND_ERROR_MESSAGE_TEMPLATE, teamMemberUsername));
    }
}
