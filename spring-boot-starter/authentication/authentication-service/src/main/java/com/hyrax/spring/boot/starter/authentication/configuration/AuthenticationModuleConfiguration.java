package com.hyrax.spring.boot.starter.authentication.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationAdminProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import com.hyrax.spring.boot.starter.authentication.rest.client.SecuredAccountRESTClient;
import com.hyrax.spring.boot.starter.authentication.rest.client.impl.SecuredAccountRESTClientImpl;
import com.hyrax.spring.boot.starter.authentication.rest.service.SecuredAccountRESTService;
import com.hyrax.spring.boot.starter.authentication.rest.service.impl.SecuredAccountRESTServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
@ComponentScan(basePackages = "com.hyrax.spring.boot.starter.authentication")
public class AuthenticationModuleConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public Client client() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecuredAccountRESTClient securedAccountRESTClient(final Client client, final AuthenticationProperties authenticationProperties, final AuthenticationAdminProperties authenticationAdminProperties) {
        return new SecuredAccountRESTClientImpl(client, authenticationProperties, authenticationAdminProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public SecuredAccountRESTService securedAccountRESTService(final SecuredAccountRESTClient securedAccountRESTClient) {
        return new SecuredAccountRESTServiceImpl(securedAccountRESTClient);
    }
}
