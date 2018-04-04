package com.hyrax.microservice.email.rest.api.app;

import com.hyrax.microservice.email.rest.api.configuration.RESTApiModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RESTApiModuleConfiguration.class)
public class EmailApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }
}