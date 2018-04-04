package com.hyrax.microservice.email.data.repository;

import com.hyrax.microservice.email.data.entity.TeamEventSubscriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamEventSubscriptionRepository extends MongoRepository<TeamEventSubscriptionEntity, String> {

    TeamEventSubscriptionEntity findByUsername(String username);
}
