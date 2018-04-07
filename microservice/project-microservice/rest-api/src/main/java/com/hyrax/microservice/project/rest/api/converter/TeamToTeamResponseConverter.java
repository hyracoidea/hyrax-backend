package com.hyrax.microservice.project.rest.api.converter;

import com.hyrax.microservice.project.rest.api.domain.response.TeamResponse;
import com.hyrax.microservice.project.service.domain.Team;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamToTeamResponseConverter implements Converter<Team, TeamResponse> {

    @Override
    public TeamResponse convert(final Team team) {
        return TeamResponse.builder()
                .teamName(team.getName())
                .description(team.getDescription())
                .ownerUsername(team.getOwnerUsername())
                .build();
    }
}
