package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.LabelEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.LabelEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.LabelEventSubscription;
import org.modelmapper.ModelMapper;

public class LabelEventSubscriptionServiceImpl extends EventSubscriptionServiceImpl<LabelEventSubscription, LabelEventSubscriptionEntity> implements LabelEventSubscriptionService {

    public LabelEventSubscriptionServiceImpl(final EventSubscriptionRepository<LabelEventSubscriptionEntity> eventSubscriptionRepository, final ModelMapper modelMapper,
                                             final Class<LabelEventSubscription> eventSubscriptionType, final Class<LabelEventSubscriptionEntity> eventSubscriptionEntityType) {
        super(eventSubscriptionRepository, modelMapper, eventSubscriptionType, eventSubscriptionEntityType);
    }
}
