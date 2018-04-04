package com.hyrax.microservice.email.data.repository;

import com.hyrax.microservice.email.data.entity.BoardEventSubscriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardEventSubscriptionRepository extends MongoRepository<BoardEventSubscriptionEntity, String> {

    BoardEventSubscriptionEntity findByUsername(String username);
}
