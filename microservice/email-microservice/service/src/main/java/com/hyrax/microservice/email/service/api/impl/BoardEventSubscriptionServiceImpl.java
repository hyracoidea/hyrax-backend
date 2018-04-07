package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.BoardEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.BoardEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;
import org.modelmapper.ModelMapper;

public class BoardEventSubscriptionServiceImpl extends EventSubscriptionServiceImpl<BoardEventSubscription, BoardEventSubscriptionEntity> implements BoardEventSubscriptionService {

    public BoardEventSubscriptionServiceImpl(final EventSubscriptionRepository<BoardEventSubscriptionEntity> eventSubscriptionRepository, final ModelMapper modelMapper,
                                             final Class<BoardEventSubscription> eventSubscriptionType, final Class<BoardEventSubscriptionEntity> eventSubscriptionEntityType) {
        super(eventSubscriptionRepository, modelMapper, eventSubscriptionType, eventSubscriptionEntityType);
    }
}
