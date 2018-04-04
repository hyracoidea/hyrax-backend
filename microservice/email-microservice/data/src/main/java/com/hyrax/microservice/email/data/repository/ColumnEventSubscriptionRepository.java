package com.hyrax.microservice.email.data.repository;

import com.hyrax.microservice.email.data.entity.ColumnEventSubscriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ColumnEventSubscriptionRepository extends MongoRepository<ColumnEventSubscriptionEntity, String> {

    ColumnEventSubscriptionEntity findByUsername(String username);
}
