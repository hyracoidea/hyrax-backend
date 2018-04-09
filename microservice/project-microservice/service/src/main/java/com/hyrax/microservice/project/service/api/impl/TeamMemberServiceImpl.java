package com.hyrax.microservice.project.service.api.impl;

import com.hyrax.microservice.project.data.dao.TeamDAO;
import com.hyrax.microservice.project.service.api.TeamMemberService;
import com.hyrax.microservice.project.service.api.impl.checker.TeamOperationChecker;
import com.hyrax.microservice.project.service.api.impl.helper.TeamEventEmailSenderHelper;
import com.hyrax.microservice.project.service.exception.team.member.TeamMemberAdditionOperationNotAllowedException;
import com.hyrax.microservice.project.service.exception.team.member.TeamMemberIsAlreadyAddedException;
import com.hyrax.microservice.project.service.exception.team.member.TeamMemberRemovalOperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMemberServiceImpl.class);

    private static final String TEAM_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE = "Team member is already added: %s";

    private final TeamDAO teamDAO;

    private final TeamOperationChecker teamOperationChecker;

    private final TeamEventEmailSenderHelper teamEventEmailSenderHelper;

    @Override
    @Transactional(readOnly = true)
    public Set<String> findAllUsernameByTeamName(final String teamName) {
        return teamDAO.findAllTeamMemberNameByTeamName(teamName);
    }

    @Override
    @Transactional
    public void add(final String username, final String teamName, final String requestedBy) {
        final boolean isOperationAllowed = teamOperationChecker.isTeamMemberAdditionOperationAllowed(teamName, requestedBy);
        if (isOperationAllowed) {
            try {
                teamDAO.addMemberToTeam(teamName, username);
                teamEventEmailSenderHelper.sendTeamMemberAdditionEmail(teamDAO.findAllTeamMemberNameByTeamName(teamName), teamName, username, requestedBy);
            } catch (final DuplicateKeyException e) {
                LOGGER.error(String.format(TEAM_MEMBER_ALREADY_ADDED_ERROR_MESSAGE_TEMPLATE, username), e);
                throw new TeamMemberIsAlreadyAddedException(username);
            }
        } else {
            throw new TeamMemberAdditionOperationNotAllowedException(requestedBy);
        }
    }

    @Override
    @Transactional
    public void remove(final String username, final String teamName, final String requestedBy) {
        final boolean isOperationAllowed = teamOperationChecker.isTeamMemberRemovalOperationAllowed(teamName, username, requestedBy);
        if (isOperationAllowed) {
            final Set<String> teamMemberUsernames = teamDAO.findAllTeamMemberNameByTeamName(teamName);
            teamDAO.deleteMemberFromTeam(teamName, username);
            teamEventEmailSenderHelper.sendTeamMemberRemovalEmail(teamMemberUsernames, teamName, username, requestedBy);
        } else {
            throw new TeamMemberRemovalOperationNotAllowedException(requestedBy);
        }
    }
}
