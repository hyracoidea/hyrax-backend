package com.hyrax.client.email.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelEventSubscriptionResponse {

    private final String username;

    private final boolean labelCreationAction;

    private final boolean labelRemovalAction;
}
