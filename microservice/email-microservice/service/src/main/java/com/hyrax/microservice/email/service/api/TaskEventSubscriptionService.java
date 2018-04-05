package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;

public interface TaskEventSubscriptionService {

    TaskEventSubscription findByUsername(String username);

    void saveOrUpdate(TaskEventSubscription taskEventSubscription);
}
