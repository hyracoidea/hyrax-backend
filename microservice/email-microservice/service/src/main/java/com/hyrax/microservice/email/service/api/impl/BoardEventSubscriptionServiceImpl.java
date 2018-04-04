package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.BoardEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.BoardEventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.BoardEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.BoardEventSubscription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardEventSubscriptionServiceImpl implements BoardEventSubscriptionService {

    private final BoardEventSubscriptionRepository boardEventSubscriptionRepository;

    private final ModelMapper modelMapper;

    @Override
    public BoardEventSubscription findByUsername(final String username) {
        return modelMapper.map(boardEventSubscriptionRepository.findByUsername(username), BoardEventSubscription.class);
    }

    @Override
    public void saveOrUpdate(final BoardEventSubscription boardEventSubscription) {
        boardEventSubscriptionRepository.save(modelMapper.map(boardEventSubscription, BoardEventSubscriptionEntity.class));
    }
}
