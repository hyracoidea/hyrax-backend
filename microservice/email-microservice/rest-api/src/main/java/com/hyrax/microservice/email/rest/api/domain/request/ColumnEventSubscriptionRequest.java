package com.hyrax.microservice.email.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnEventSubscriptionRequest {

    private String username;

    private boolean columnCreationAction;

    private boolean columnRemovalAction;
}
