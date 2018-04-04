package com.hyrax.microservice.email.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnEventSubscriptionResponse {

    private String username;

    private boolean columnCreationAction;

    private boolean columnRemovalAction;
}
