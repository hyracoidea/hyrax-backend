package com.hyrax.microservice.email.rest.api.converter;

import com.hyrax.microservice.email.rest.api.domain.response.TeamEventSubscriptionResponse;
import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TeamEventSubscriptionToTeamEventSubscriptionResponseConverter implements Converter<TeamEventSubscription, TeamEventSubscriptionResponse> {

    @Override
    public TeamEventSubscriptionResponse convert(final TeamEventSubscription teamEventSubscription) {
        return TeamEventSubscriptionResponse.builder()
                .username(teamEventSubscription.getUsername())
                .teamCreationAction(teamEventSubscription.isTeamCreationAction())
                .teamMemberAdditionAction(teamEventSubscription.isTeamMemberAdditionAction())
                .teamMemberRemovalAction(teamEventSubscription.isTeamMemberRemovalAction())
                .teamRemovalAction(teamEventSubscription.isTeamRemovalAction())
                .build();
    }
}
