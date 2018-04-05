package com.hyrax.microservice.email.data.repository;

import com.hyrax.microservice.email.data.entity.TaskEventSubscriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskEventSubscriptionRepository extends MongoRepository<TaskEventSubscriptionEntity, String> {

    TaskEventSubscriptionEntity findByUsername(String username);
}
