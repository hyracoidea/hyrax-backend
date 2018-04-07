package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Team;
import com.hyrax.microservice.project.service.exception.team.TeamAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    Optional<Team> findByTeamName(String teamName);

    List<Team> findAllByUsername(String username);

    void save(Team team) throws TeamAlreadyExistsException;

    void remove(String teamName, String requestedBy);
}
