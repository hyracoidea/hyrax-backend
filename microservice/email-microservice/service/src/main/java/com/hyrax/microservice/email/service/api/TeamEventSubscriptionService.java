package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;

public interface TeamEventSubscriptionService {

    TeamEventSubscription findByUsername(String username);

    void saveOrUpdate(TeamEventSubscription teamEventSubscription);
}
