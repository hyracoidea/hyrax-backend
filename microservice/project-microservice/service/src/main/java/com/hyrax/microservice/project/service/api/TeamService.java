package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Team;
import com.hyrax.microservice.project.service.exception.team.TeamAlreadyExistsException;

import java.util.Optional;

public interface TeamService {

    Optional<Team> findByTeamName(String teamName);

    void save(Team team) throws TeamAlreadyExistsException;

    void remove(String teamName, String requestedBy);
}
