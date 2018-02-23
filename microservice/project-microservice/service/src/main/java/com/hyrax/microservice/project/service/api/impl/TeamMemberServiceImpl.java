package com.hyrax.microservice.project.service.api.impl;

import com.google.common.collect.Lists;
import com.hyrax.client.account.api.response.HyraxResponse;
import com.hyrax.client.account.api.response.UsernameWrapperResponse;
import com.hyrax.client.account.api.service.AccountRESTService;
import com.hyrax.microservice.project.data.entity.TeamEntity;
import com.hyrax.microservice.project.data.mapper.TeamMapper;
import com.hyrax.microservice.project.data.mapper.TeamMemberMapper;
import com.hyrax.microservice.project.service.api.TeamMemberService;
import com.hyrax.microservice.project.service.exception.TeamMemberIsAlreadyAddedException;
import com.hyrax.microservice.project.service.exception.TeamMemberNotFoundException;
import com.hyrax.microservice.project.service.exception.TeamNotFoundException;
import com.hyrax.microservice.project.service.exception.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    private static final String TEAM_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE = "Team member is already added: %s";

    private final AccountRESTService accountRESTService;

    private final TeamMapper teamMapper;

    private final TeamMemberMapper teamMemberMapper;

    @Autowired
    public TeamMemberServiceImpl(final AccountRESTService accountRESTService, final TeamMapper teamMapper, final TeamMemberMapper teamMemberMapper) {
        this.accountRESTService = accountRESTService;
        this.teamMapper = teamMapper;
        this.teamMemberMapper = teamMemberMapper;
    }

    @Override
    @Transactional
    public void add(final String username, final String teamName, final String requestedBy) {
        final List<String> existingUsernames = retrieveUsernames();
        final List<String> teamMemberUsernames = teamMemberMapper.selectAllUsernameByTeamName(teamName);
        final TeamEntity team = teamMapper.selectByName(teamName);

        validateUsernamesByExistence(username, requestedBy, existingUsernames);
        validateByTeamExistence(team, teamName);
        validateByTeamMemberAdditionRole(requestedBy, team.getOwnerUsername(), teamMemberUsernames);
        validateByAlreadyTeamMember(username, teamMemberUsernames);

        try {
            teamMemberMapper.insert(username, teamName);
        } catch (final DuplicateKeyException e) {
            LOGGER.error(String.format(TEAM_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE, username), e);
            throw new TeamMemberIsAlreadyAddedException(username);
        }

    }

    private void validateUsernamesByExistence(final String username, final String requestedBy, final List<String> existingUsernames) throws UsernameNotFoundException {
        if (!existingUsernames.contains(username)) {
            throw new UsernameNotFoundException(username);
        } else if (!existingUsernames.contains(requestedBy)) {
            throw new UsernameNotFoundException(requestedBy);
        }
    }

    private void validateByTeamExistence(final TeamEntity teamEntity, final String teamName) {
        if (Objects.isNull(teamEntity)) {
            throw new TeamNotFoundException(teamName);
        }
    }

    private void validateByTeamMemberAdditionRole(final String requestedBy, final String teamOwnerUsername, final List<String> teamMemberUsernames) {
        final boolean isTeamOwner = teamOwnerUsername.equals(requestedBy);
        final boolean isTeamMember = teamMemberUsernames.contains(requestedBy);
        if (!isTeamOwner && !isTeamMember) {
            throw new TeamMemberNotFoundException(requestedBy);
        }
    }

    private void validateByAlreadyTeamMember(final String username, final List<String> teamMemberUsernames) {
        if (teamMemberUsernames.contains(username)) {
            throw new TeamMemberIsAlreadyAddedException(username);
        }
    }

    private List<String> retrieveUsernames() {
        final List<String> result = Lists.newArrayList();

        final HyraxResponse<UsernameWrapperResponse> response = accountRESTService.retrieveAllUsernames();
        switch (response.getResponseStatus()) {
            case SUCCESSFUL:
                LOGGER.info("Account REST service call was successful");
                result.addAll(response.getPayload().getUsernames());
                break;
            case CLIENT_ERROR:
                LOGGER.warn("Account REST service call was unsuccessful, client error happened");
                break;
            case SERVER_ERROR:
                LOGGER.error("Account REST service call was unsuccessful, server error happened");
                break;
            default:
                break;
        }

        return result;
    }

}
