package com.hyrax.microservice.project.service.api;

import com.hyrax.microservice.project.service.domain.Team;
import com.hyrax.microservice.project.service.exception.TeamAlreadyExistsException;

import java.util.Optional;

public interface TeamService {

    boolean existByName(String name);

    Optional<Team> findByName(String name);

    void save(Team team) throws TeamAlreadyExistsException;
}
