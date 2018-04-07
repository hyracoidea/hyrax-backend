package com.hyrax.microservice.project.service.api.impl;

import com.google.common.base.Preconditions;
import com.hyrax.microservice.project.data.dao.TeamDAO;
import com.hyrax.microservice.project.data.entity.TeamEntity;
import com.hyrax.microservice.project.service.api.TeamService;
import com.hyrax.microservice.project.service.domain.Team;
import com.hyrax.microservice.project.service.exception.team.TeamAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.team.TeamRemovalOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    private static final String ERROR_MESSAGE_TEMPLATE = "Team already exists with this name=%s";

    private final TeamDAO teamDAO;

    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> findByTeamName(final String teamName) {
        Team team = null;
        final Optional<TeamEntity> teamEntity = teamDAO.findByTeamName(teamName);
        if (teamEntity.isPresent()) {
            team = modelMapper.map(teamEntity.get(), Team.class);
        }
        return Optional.ofNullable(team);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> findAllByUsername(final String username) {
        return teamDAO.findAllByUsername(username)
                .stream()
                .map(teamEntity -> modelMapper.map(teamEntity, Team.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(final Team team) throws TeamAlreadyExistsException {
        Preconditions.checkArgument(Objects.nonNull(team));
        try {
            LOGGER.info("Trying to save the team [team={}]", team);
            teamDAO.save(modelMapper.map(team, TeamEntity.class));
            LOGGER.info("Team={} saving was successful", team);
        } catch (final DuplicateKeyException e) {
            throwTeamAlreadyExistsException(team.getName(), e);
        }
    }

    @Override
    @Transactional
    public void remove(final String teamName, final String requestedBy) {
        final Optional<Team> result = findByTeamName(teamName);

        if (result.isPresent()) {
            if (result.get().getOwnerUsername().equals(requestedBy)) {
                teamDAO.delete(teamName);
            } else {
                throw new TeamRemovalOperationNotAllowedException(requestedBy);
            }
        }
    }

    private TeamAlreadyExistsException throwTeamAlreadyExistsException(final String teamName, final Exception e) {
        final String errorMessage = String.format(ERROR_MESSAGE_TEMPLATE, teamName);
        LOGGER.error(errorMessage, e);
        throw new TeamAlreadyExistsException(errorMessage);
    }
}
