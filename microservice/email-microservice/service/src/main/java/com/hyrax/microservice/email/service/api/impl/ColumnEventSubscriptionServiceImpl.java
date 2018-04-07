package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.ColumnEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.ColumnEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;
import org.modelmapper.ModelMapper;

public class ColumnEventSubscriptionServiceImpl extends EventSubscriptionServiceImpl<ColumnEventSubscription, ColumnEventSubscriptionEntity>
        implements ColumnEventSubscriptionService {

    public ColumnEventSubscriptionServiceImpl(final EventSubscriptionRepository<ColumnEventSubscriptionEntity> eventSubscriptionRepository, final ModelMapper modelMapper,
                                              final Class<ColumnEventSubscription> eventSubscriptionType, final Class<ColumnEventSubscriptionEntity> eventSubscriptionEntityType) {
        super(eventSubscriptionRepository, modelMapper, eventSubscriptionType, eventSubscriptionEntityType);
    }
}
