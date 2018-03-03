package com.hyrax.microservice.project.service.exception.team.member;

public class TeamMemberRemovalOperationNotAllowedException extends TeamMemberOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Team member removal operation not allowed to %s";

    public TeamMemberRemovalOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
