package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.BoardEventSubscriptionRequest;
import com.hyrax.client.email.api.service.BoardEmailEventSubscriptionRESTService;
import com.hyrax.client.email.api.service.impl.BoardEmailEventSubscriptionRESTServiceImpl;
import com.hyrax.client.email.api.service.impl.EmailEventSubscriptionRESTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class BoardEmailEventSubscriptionConfiguration {

    @Autowired
    private EmailEventSubscriptionRESTClientProperties emailEventSubscriptionRESTClientProperties;

    @Autowired
    private EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    @Bean
    @ConditionalOnMissingBean(name = "boardEmailEventSubscriptionJerseyClient")
    public Client boardEmailEventSubscriptionJerseyClient() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean(name = "boardEmailEventSubscriptionRESTClient")
    public EmailEventSubscriptionRESTClient<BoardEventSubscriptionRequest> boardEmailEventSubscriptionRESTClient(final Client boardEmailEventSubscriptionJerseyClient) {
        return new EmailEventSubscriptionRESTClient<>(boardEmailEventSubscriptionJerseyClient, emailEventSubscriptionRESTClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public BoardEmailEventSubscriptionRESTService boardEmailEventSubscriptionRESTService(final EmailEventSubscriptionRESTClient<BoardEventSubscriptionRequest> boardEmailEventSubscriptionRESTClient) {
        return new BoardEmailEventSubscriptionRESTServiceImpl(boardEmailEventSubscriptionRESTClient, emailEventSubscriptionRESTEndpointProperties);
    }
}
