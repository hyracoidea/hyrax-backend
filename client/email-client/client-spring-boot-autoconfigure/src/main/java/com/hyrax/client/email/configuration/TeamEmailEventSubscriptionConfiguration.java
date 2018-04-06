package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.TeamEventSubscriptionRequest;
import com.hyrax.client.email.api.service.TeamEmailEventSubscriptionRESTService;
import com.hyrax.client.email.api.service.impl.EmailEventSubscriptionRESTClient;
import com.hyrax.client.email.api.service.impl.TeamEmailEventSubscriptionRESTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class TeamEmailEventSubscriptionConfiguration {

    @Autowired
    private EmailEventSubscriptionRESTClientProperties emailEventSubscriptionRESTClientProperties;

    @Autowired
    private EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    @Bean
    @ConditionalOnMissingBean(name = "teamEmailEventSubscriptionJerseyClient")
    public Client teamEmailEventSubscriptionJerseyClient() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean(name = "teamEmailEventSubscriptionRESTClient")
    public EmailEventSubscriptionRESTClient<TeamEventSubscriptionRequest> teamEmailEventSubscriptionRESTClient(final Client teamEmailEventSubscriptionJerseyClient) {
        return new EmailEventSubscriptionRESTClient<>(teamEmailEventSubscriptionJerseyClient, emailEventSubscriptionRESTClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TeamEmailEventSubscriptionRESTService teamEmailEventSubscriptionRESTService(final EmailEventSubscriptionRESTClient<TeamEventSubscriptionRequest> teamEmailEventSubscriptionRESTClient) {
        return new TeamEmailEventSubscriptionRESTServiceImpl(teamEmailEventSubscriptionRESTClient, emailEventSubscriptionRESTEndpointProperties);
    }
}
