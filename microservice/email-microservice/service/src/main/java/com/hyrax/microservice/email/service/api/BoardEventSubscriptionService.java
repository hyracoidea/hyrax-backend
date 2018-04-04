package com.hyrax.microservice.email.service.api;

import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;

public interface BoardEventSubscriptionService {

    BoardEventSubscription findByUsername(String username);

    void saveOrUpdate(BoardEventSubscription boardEventSubscription);
}
