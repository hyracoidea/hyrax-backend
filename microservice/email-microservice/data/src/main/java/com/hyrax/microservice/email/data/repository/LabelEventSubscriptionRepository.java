package com.hyrax.microservice.email.data.repository;

import com.hyrax.microservice.email.data.entity.LabelEventSubscriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LabelEventSubscriptionRepository extends MongoRepository<LabelEventSubscriptionEntity, String> {

    LabelEventSubscriptionEntity findByUsername(String username);
}
