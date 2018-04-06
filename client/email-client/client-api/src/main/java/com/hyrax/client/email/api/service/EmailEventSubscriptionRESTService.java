package com.hyrax.client.email.api.service;

import com.hyrax.client.email.api.request.BaseEventSubscriptionRequest;

public interface EmailEventSubscriptionRESTService<T extends BaseEventSubscriptionRequest> {

    void createEventSubscription(String username);

    void updateEventSubscription(T eventSubscriptionRequest);
}
