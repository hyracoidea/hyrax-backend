package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.LabelEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.LabelEventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.LabelEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.LabelEventSubscription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelEventSubscriptionServiceImpl implements LabelEventSubscriptionService {

    private final LabelEventSubscriptionRepository labelEventSubscriptionRepository;

    private final ModelMapper modelMapper;

    @Override
    public LabelEventSubscription findByUsername(final String username) {
        return modelMapper.map(labelEventSubscriptionRepository.findByUsername(username), LabelEventSubscription.class);
    }

    @Override
    public void saveOrUpdate(LabelEventSubscription labelEventSubscription) {
        labelEventSubscriptionRepository.save(modelMapper.map(labelEventSubscription, LabelEventSubscriptionEntity.class));
    }
}
