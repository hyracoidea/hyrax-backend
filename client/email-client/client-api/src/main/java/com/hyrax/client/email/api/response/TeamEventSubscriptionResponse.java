package com.hyrax.client.email.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamEventSubscriptionResponse {

    private final String username;

    private final boolean teamCreationAction;

    private final boolean teamRemovalAction;

    private final boolean teamMemberAdditionAction;

    private final boolean teamMemberRemovalAction;
}
