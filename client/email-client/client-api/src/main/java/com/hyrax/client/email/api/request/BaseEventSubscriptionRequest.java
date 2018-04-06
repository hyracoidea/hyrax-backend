package com.hyrax.client.email.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseEventSubscriptionRequest {

    private final String username;
}
