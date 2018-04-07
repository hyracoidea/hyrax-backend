package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.TaskEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.TaskEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;
import org.modelmapper.ModelMapper;

public class TaskEventSubscriptionServiceImpl extends EventSubscriptionServiceImpl<TaskEventSubscription, TaskEventSubscriptionEntity> implements TaskEventSubscriptionService {

    public TaskEventSubscriptionServiceImpl(final EventSubscriptionRepository<TaskEventSubscriptionEntity> eventSubscriptionRepository, final ModelMapper modelMapper,
                                            final Class<TaskEventSubscription> eventSubscriptionType, final Class<TaskEventSubscriptionEntity> eventSubscriptionEntityType) {
        super(eventSubscriptionRepository, modelMapper, eventSubscriptionType, eventSubscriptionEntityType);
    }
}
