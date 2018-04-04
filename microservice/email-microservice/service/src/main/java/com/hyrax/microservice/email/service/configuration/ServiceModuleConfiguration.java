package com.hyrax.microservice.email.service.configuration;

import com.hyrax.microservice.email.data.configuration.DataModuleConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.hyrax.microservice.email.service")
@Import(DataModuleConfiguration.class)
public class ServiceModuleConfiguration {
}
