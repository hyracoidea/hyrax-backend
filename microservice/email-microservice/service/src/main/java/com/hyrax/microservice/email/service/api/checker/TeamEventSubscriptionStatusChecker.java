package com.hyrax.microservice.email.service.api.checker;

import com.hyrax.microservice.email.service.api.TeamEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TeamEventSubscriptionStatusChecker {

    private final TeamEventSubscriptionService teamEventSubscriptionService;

    public boolean isSubscribedByTeamEventCategory(final String username, final SubEventCategory subEventCategory) {
        boolean result = false;
        final Optional<TeamEventSubscription> teamEventSubscription = teamEventSubscriptionService.findByUsername(username);
        if (teamEventSubscription.isPresent()) {
            switch (subEventCategory) {
                case CREATION:
                    result = teamEventSubscription.get().isTeamCreationAction();
                    break;
                case MEMBER_ADDITION:
                    result = teamEventSubscription.get().isTeamMemberAdditionAction();
                    break;
                case MEMBER_REMOVAL:
                    result = teamEventSubscription.get().isTeamMemberRemovalAction();
                    break;
                case REMOVAL:
                    result = teamEventSubscription.get().isTeamRemovalAction();
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
