package com.hyrax.microservice.project.rest.api.configuration;

import com.hyrax.microservice.project.service.configuration.ServiceModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceModuleConfiguration.class)
@ComponentScan(basePackages = "com.hyrax.microservice.project.rest.api")
public class RESTApiModuleConfiguration {
}