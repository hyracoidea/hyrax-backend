package com.hyrax.microservice.account.rest.api.application;

import com.hyrax.microservice.account.rest.api.configuration.RESTApiModuleConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RESTApiModuleConfiguration.class)
public class AccountApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
