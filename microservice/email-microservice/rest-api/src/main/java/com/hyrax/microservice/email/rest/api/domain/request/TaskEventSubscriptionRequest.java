package com.hyrax.microservice.email.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskEventSubscriptionRequest {

    private String username;

    private boolean taskCreationAction;

    private boolean taskRemovalAction;
}
