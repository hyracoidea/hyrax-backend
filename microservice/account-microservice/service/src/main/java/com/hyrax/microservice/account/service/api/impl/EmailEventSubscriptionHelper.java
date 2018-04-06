package com.hyrax.microservice.account.service.api.impl;

import com.hyrax.client.email.api.service.EmailEventSubscriptionRESTService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class EmailEventSubscriptionHelper {

    private final Set<EmailEventSubscriptionRESTService> emailEventSubscriptionRESTServices;

    @Async
    public void createDefaultEmailEventSubscriptionsBy(final String username) {
        emailEventSubscriptionRESTServices.forEach(emailEventSubscriptionRESTService -> emailEventSubscriptionRESTService.createEventSubscription(username));
    }
}
