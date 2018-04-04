package com.hyrax.microservice.email.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamEventSubscriptionRequest {

    private final String username;

    private final boolean teamCreationAction;

    private final boolean teamRemovalAction;

    private final boolean teamMemberAdditionAction;

    private final boolean teamMemberRemovalAction;
}
