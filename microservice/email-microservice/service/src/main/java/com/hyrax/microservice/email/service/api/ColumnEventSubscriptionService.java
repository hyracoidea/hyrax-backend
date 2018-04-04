package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;

public interface ColumnEventSubscriptionService {

    ColumnEventSubscription findByUsername(String username);

    void saveOrUpdate(ColumnEventSubscription columnEventSubscription);
}
