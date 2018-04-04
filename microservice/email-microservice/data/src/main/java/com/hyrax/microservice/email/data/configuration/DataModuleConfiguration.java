package com.hyrax.microservice.email.data.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.hyrax.microservice.email.data.repository")
public class DataModuleConfiguration {
}
