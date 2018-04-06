package com.hyrax.client.email.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnEventSubscriptionResponse {

    private final String username;

    private final boolean columnCreationAction;

    private final boolean columnRemovalAction;
}
