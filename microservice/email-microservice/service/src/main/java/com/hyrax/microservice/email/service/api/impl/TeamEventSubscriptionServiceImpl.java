package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.TeamEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.TeamEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;
import org.modelmapper.ModelMapper;

public class TeamEventSubscriptionServiceImpl extends EventSubscriptionServiceImpl<TeamEventSubscription, TeamEventSubscriptionEntity> implements TeamEventSubscriptionService {

    public TeamEventSubscriptionServiceImpl(final EventSubscriptionRepository<TeamEventSubscriptionEntity> eventSubscriptionRepository, final ModelMapper modelMapper,
                                            final Class<TeamEventSubscription> eventSubscriptionType, final Class<TeamEventSubscriptionEntity> eventSubscriptionEntityType) {
        super(eventSubscriptionRepository, modelMapper, eventSubscriptionType, eventSubscriptionEntityType);
    }
}
