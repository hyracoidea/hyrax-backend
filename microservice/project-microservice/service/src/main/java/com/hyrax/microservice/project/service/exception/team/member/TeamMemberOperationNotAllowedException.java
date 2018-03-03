package com.hyrax.microservice.project.service.exception.team.member;

public class TeamMemberOperationNotAllowedException extends RuntimeException {

    public TeamMemberOperationNotAllowedException(final String message) {
        super(message);
    }
}
