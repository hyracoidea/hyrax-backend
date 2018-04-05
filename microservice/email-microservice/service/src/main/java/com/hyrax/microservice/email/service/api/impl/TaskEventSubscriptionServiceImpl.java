package com.hyrax.microservice.email.service.api.impl;

import com.hyrax.microservice.email.data.entity.TaskEventSubscriptionEntity;
import com.hyrax.microservice.email.data.repository.TaskEventSubscriptionRepository;
import com.hyrax.microservice.email.service.api.TaskEventSubscriptionService;
import com.hyrax.microservice.email.service.api.model.TaskEventSubscription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskEventSubscriptionServiceImpl implements TaskEventSubscriptionService {

    private final TaskEventSubscriptionRepository taskEventSubscriptionRepository;

    private final ModelMapper modelMapper;

    @Override
    public TaskEventSubscription findByUsername(final String username) {
        return modelMapper.map(taskEventSubscriptionRepository.findByUsername(username), TaskEventSubscription.class);
    }

    @Override
    public void saveOrUpdate(final TaskEventSubscription taskEventSubscription) {
        taskEventSubscriptionRepository.save(modelMapper.map(taskEventSubscription, TaskEventSubscriptionEntity.class));
    }
}
