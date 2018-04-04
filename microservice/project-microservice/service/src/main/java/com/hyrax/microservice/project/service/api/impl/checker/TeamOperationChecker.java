package com.hyrax.microservice.project.service.api.impl.checker;

import com.hyrax.microservice.project.data.dao.TeamDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TeamOperationChecker {

    private final TeamDAO teamDAO;

    public boolean isTeamMemberAdditionOperationAllowed(final String teamName, final String requestedBy) {
        final Optional<String> ownerUsername = teamDAO.findByTeamName(teamName).map(team -> team.getOwnerUsername());
        final List<String> memberUsernames = teamDAO.findAllTeamMemberNameByTeamName(teamName);

        return ownerUsername.isPresent() && (ownerUsername.get().equals(requestedBy)) || (memberUsernames.contains(requestedBy));
    }

    public boolean isTeamMemberRemovalOperationAllowed(final String teamName, final String username, final String requestedBy) {
        final Optional<String> ownerUsername = teamDAO.findByTeamName(teamName).map(team -> team.getOwnerUsername());

        return ownerUsername.isPresent() && (canRemoveYourself(username, requestedBy, ownerUsername.get()) || canRemoveOtherMembers(username, requestedBy, ownerUsername.get()));
    }

    private boolean canRemoveYourself(final String username, final String requestedBy, final String teamOwnerUsername) {
        return username.equals(requestedBy) && !teamOwnerUsername.equals(requestedBy);
    }

    private boolean canRemoveOtherMembers(final String username, final String requestedBy, final String teamOwnerUsername) {
        return !username.equals(requestedBy) && teamOwnerUsername.equals(requestedBy);
    }
}
