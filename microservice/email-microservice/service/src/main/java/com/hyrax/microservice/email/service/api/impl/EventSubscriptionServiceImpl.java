package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.repository.EventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.EventSubscriptionService;
import org.modelmapper.ModelMapper;

import java.util.Objects;
import java.util.Optional;

public class EventSubscriptionServiceImpl<T, E> implements EventSubscriptionService<T> {

    private final EventSubscriptionRepository<E> eventSubscriptionRepository;

    private final ModelMapper modelMapper;

    private final Class<T> eventSubscriptionType;

    private final Class<E> eventSubscriptionEntityType;

    public EventSubscriptionServiceImpl(final EventSubscriptionRepository<E> eventSubscriptionRepository, final ModelMapper modelMapper,
                                        final Class<T> eventSubscriptionType, final Class<E> eventSubscriptionEntityType) {
        this.eventSubscriptionRepository = eventSubscriptionRepository;
        this.modelMapper = modelMapper;
        this.eventSubscriptionType = eventSubscriptionType;
        this.eventSubscriptionEntityType = eventSubscriptionEntityType;
    }

    @Override
    public Optional<T> findByUsername(final String username) {
        T eventSubscription = null;

        final E eventSubscriptionEntity = eventSubscriptionRepository.findByUsername(username);
        if (Objects.nonNull(eventSubscriptionEntity)) {
            eventSubscription = modelMapper.map(eventSubscriptionEntity, eventSubscriptionType);
        }
        return Optional.ofNullable(eventSubscription);
    }

    @Override
    public void saveOrUpdate(final T eventSubscription) {
        eventSubscriptionRepository.save(modelMapper.map(eventSubscription, eventSubscriptionEntityType));
    }
}
