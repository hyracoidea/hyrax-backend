package com.hyrax.microservice.email.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelEventSubscriptionRequest {

    private String username;

    private boolean labelCreationAction;

    private boolean labelRemovalAction;
}
