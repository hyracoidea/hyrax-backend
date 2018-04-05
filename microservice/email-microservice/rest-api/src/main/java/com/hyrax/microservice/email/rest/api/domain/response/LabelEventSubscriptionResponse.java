package com.hyrax.microservice.email.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelEventSubscriptionResponse {

    private String username;

    private boolean labelCreationAction;

    private boolean labelRemovalAction;
}
