package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.request.TeamCreationRequest;
import com.hyrax.microservice.project.rest.api.security.AuthenticationUserDetailsHelper;
import com.hyrax.microservice.project.service.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TeamCreationRequestToTeamConverter implements Converter<TeamCreationRequest, Team> {

    private final AuthenticationUserDetailsHelper authenticationUserDetailsHelper;

    @Autowired
    public TeamCreationRequestToTeamConverter(final AuthenticationUserDetailsHelper authenticationUserDetailsHelper) {
        this.authenticationUserDetailsHelper = authenticationUserDetailsHelper;
    }

    @Override
    public Team convert(final TeamCreationRequest teamCreationRequest) {
        Team result = null;

        if (Objects.nonNull(teamCreationRequest)) {
            result = Team.builder()
                    .name(teamCreationRequest.getName())
                    .description(teamCreationRequest.getDescription())
                    .ownerUsername(authenticationUserDetailsHelper.getUsername())
                    .build();
        }

        return result;
    }
}
