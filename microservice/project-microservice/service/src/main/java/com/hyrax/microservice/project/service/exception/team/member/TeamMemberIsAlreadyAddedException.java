package com.hyrax.microservice.project.service.exception.team.member;

public class TeamMemberIsAlreadyAddedException extends RuntimeException {

    private static final String TEAM_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE = "Team member is already added: %s";

    public TeamMemberIsAlreadyAddedException(final String teamMemberUsername) {
        super(String.format(TEAM_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE, teamMemberUsername));
    }
}
