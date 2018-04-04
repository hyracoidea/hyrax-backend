package com.hyrax.microservice.email.rest.api.configuration;

import com.hyrax.microservice.email.service.configuration.ServiceModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(ServiceModuleConfiguration.class)
@ComponentScan(basePackages = "com.hyrax.microservice.email.rest.api.controller")
public class RESTApiModuleConfiguration {
}
