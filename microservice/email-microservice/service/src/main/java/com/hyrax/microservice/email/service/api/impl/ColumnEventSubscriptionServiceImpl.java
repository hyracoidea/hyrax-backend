package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.ColumnEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.ColumnEventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.ColumnEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.ColumnEventSubscription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ColumnEventSubscriptionServiceImpl implements ColumnEventSubscriptionService {

    private final ColumnEventSubscriptionRepository columnEventSubscriptionRepository;

    private final ModelMapper modelMapper;

    @Override
    public ColumnEventSubscription findByUsername(final String username) {
        return modelMapper.map(columnEventSubscriptionRepository.findByUsername(username), ColumnEventSubscription.class);
    }

    @Override
    public void saveOrUpdate(final ColumnEventSubscription columnEventSubscription) {
        columnEventSubscriptionRepository.save(modelMapper.map(columnEventSubscription, ColumnEventSubscriptionEntity.class));
    }
}
