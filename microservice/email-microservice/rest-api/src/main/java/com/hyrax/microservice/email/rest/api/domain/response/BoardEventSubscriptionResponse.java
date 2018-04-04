package com.hyrax.microservice.email.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardEventSubscriptionResponse {

    private final String username;

    private final boolean boardCreationAction;

    private final boolean boardRemovalAction;

    private final boolean boardMemberAdditionAction;

    private final boolean boardMemberRemovalAction;
}
