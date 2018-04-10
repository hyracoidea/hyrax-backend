package com.hyrax.microservice.project.service.configuration;

import com.hyrax.microservice.project.data.configuration.DataModuleConfiguration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataModuleConfiguration.class, SpringAsyncConfig.class})
@ComponentScan(basePackages = "com.hyrax.microservice.project.service.api.impl")
public class ServiceModuleConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}