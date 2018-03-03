package com.hyrax.microservice.project.service.api.impl;

import com.google.common.base.Preconditions;
import com.hyrax.microservice.project.data.entity.TeamEntity;
import com.hyrax.microservice.project.data.mapper.TeamMapper;
import com.hyrax.microservice.project.data.mapper.TeamMemberMapper;
import com.hyrax.microservice.project.service.api.TeamService;
import com.hyrax.microservice.project.service.domain.Team;
import com.hyrax.microservice.project.service.exception.team.TeamAlreadyExistsException;
import com.hyrax.microservice.project.service.exception.team.TeamRemovalOperationNotAllowedException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    private static final String ERROR_MESSAGE_TEMPLATE = "Team already exists with this name=%s";

    private final TeamMapper teamMapper;

    private final TeamMemberMapper teamMemberMapper;

    private final ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(final TeamMapper teamMapper, final TeamMemberMapper teamMemberMapper, final ModelMapper modelMapper) {
        this.teamMapper = teamMapper;
        this.teamMemberMapper = teamMemberMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByName(final String name) {
        return findByName(name).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Team> findByName(final String name) {
        Team team = null;
        final TeamEntity teamEntity = teamMapper.selectByName(name);
        if (Objects.nonNull(teamEntity)) {
            team = modelMapper.map(teamEntity, Team.class);
        }
        return Optional.ofNullable(team);
    }

    @Override
    public void save(final Team team) throws TeamAlreadyExistsException {
        Preconditions.checkArgument(Objects.nonNull(team));

        try {
            if (!existByName(team.getName())) {
                LOGGER.info("Team={} seems to be valid, trying to save...", team);
                final TeamEntity teamEntity = modelMapper.map(team, TeamEntity.class);
                teamMapper.insert(teamEntity);
                LOGGER.info("Team={} saving was successful", team);
            } else {
                throwTeamAlreadyExistsException(team.getName());
            }
        } catch (final DuplicateKeyException e) {
            throwTeamAlreadyExistsException(team.getName(), e);
        }
    }

    @Override
    @Transactional
    public void remove(final String teamName, final String requestedBy) {
        final Optional<Team> result = findByName(teamName);

        if (result.isPresent()) {
            if (result.get().getOwnerUsername().equals(requestedBy)) {
                teamMemberMapper.deleteAllByTeamName(teamName);
                teamMapper.delete(teamName);
            } else {
                throw new TeamRemovalOperationNotAllowedException(requestedBy);
            }
        }
    }

    private TeamAlreadyExistsException throwTeamAlreadyExistsException(final String teamName, final Exception... e) {
        final String errorMessage = String.format(ERROR_MESSAGE_TEMPLATE, teamName);
        LOGGER.error(errorMessage, e);
        throw new TeamAlreadyExistsException(errorMessage);
    }
}
