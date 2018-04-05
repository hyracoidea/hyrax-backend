package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.LabelEventSubscription;

public interface LabelEventSubscriptionService {

    LabelEventSubscription findByUsername(String username);

    void saveOrUpdate(LabelEventSubscription labelEventSubscription);
}
