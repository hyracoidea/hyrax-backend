package com.hyrax.microservice.sample.rest.api.application;

import com.hyrax.microservice.sample.rest.api.configuration.RESTApiModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RESTApiModuleConfiguration.class)
public class SampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
