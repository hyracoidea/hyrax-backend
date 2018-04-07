package com.hyrax.microservice.email.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EventSubscriptionRepository<T> extends MongoRepository<T, String> {

    T findByUsername(String username);

}
