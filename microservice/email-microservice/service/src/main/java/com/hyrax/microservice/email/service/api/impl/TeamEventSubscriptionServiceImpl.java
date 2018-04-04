package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.TeamEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.TeamEventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.TeamEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TeamEventSubscription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamEventSubscriptionServiceImpl implements TeamEventSubscriptionService {

    private final TeamEventSubscriptionRepository teamEventSubscriptionRepository;

    private final ModelMapper modelMapper;

    @Override
    public TeamEventSubscription findByUsername(final String username) {
        return modelMapper.map(teamEventSubscriptionRepository.findByUsername(username), TeamEventSubscription.class);
    }

    @Override
    public void saveOrUpdate(final TeamEventSubscription teamEventSubscription) {
        teamEventSubscriptionRepository.save(modelMapper.map(teamEventSubscription, TeamEventSubscriptionEntity.class));
    }
}
