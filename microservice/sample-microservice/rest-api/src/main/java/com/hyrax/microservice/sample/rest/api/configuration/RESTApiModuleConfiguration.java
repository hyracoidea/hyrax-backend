package com.hyrax.microservice.sample.rest.api.configuration;

import com.hyrax.microservice.sample.service.configuration.ServiceModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceModuleConfiguration.class)
@ComponentScan(basePackages = "com.hyrax.microservice.sample.rest.api")
public class RESTApiModuleConfiguration {
}
