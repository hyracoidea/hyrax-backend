package com.hyrax.microservice.project.rest.api.application;

import com.hyrax.microservice.project.rest.api.configuration.RESTApiModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RESTApiModuleConfiguration.class)
public class ProjectApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
