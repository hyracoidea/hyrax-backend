package com.hyrax.client.email.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskEventSubscriptionResponse {

    private final String username;

    private final boolean taskCreationAction;

    private final boolean taskRemovalAction;
}
