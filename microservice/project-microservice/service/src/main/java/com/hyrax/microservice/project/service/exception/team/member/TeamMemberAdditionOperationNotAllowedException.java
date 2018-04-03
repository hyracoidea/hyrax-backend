package com.hyrax.microservice.project.service.exception.team.member;

public class TeamMemberAdditionOperationNotAllowedException extends TeamMemberOperationNotAllowedException {

    private static final String ERROR_MESSAGE_TEMPLATE = "Team member addition operation not allowed to %s";

    public TeamMemberAdditionOperationNotAllowedException(final String requestedBy) {
        super(String.format(ERROR_MESSAGE_TEMPLATE, requestedBy));
    }
}
