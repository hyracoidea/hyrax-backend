package com.hyrax.microservice.email.service.api;

import java.util.Optional;

public interface EventSubscriptionService<T> {

    Optional<T> findByUsername(String username);

    void saveOrUpdate(T eventSubscription);
}
