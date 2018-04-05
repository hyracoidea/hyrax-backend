package com.hyrax.microservice.email.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskEventSubscriptionResponse {

    private String username;

    private boolean taskCreationAction;

    private boolean taskRemovalAction;
}
