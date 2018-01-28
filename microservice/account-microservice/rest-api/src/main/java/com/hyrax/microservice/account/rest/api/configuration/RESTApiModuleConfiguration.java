package com.hyrax.microservice.account.rest.api.configuration;

import com.hyrax.microservice.account.service.configuration.ServiceModuleConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import(ServiceModuleConfiguration.class)
@ComponentScan(basePackages = "com.hyrax.microservice.account.rest.api")
public class RESTApiModuleConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
