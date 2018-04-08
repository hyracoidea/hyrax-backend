package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.ColumnEventSubscriptionRequest;
import com.hyrax.client.email.api.service.ColumnEmailEventSubscriptionRESTService;
import com.hyrax.client.email.api.service.impl.ColumnEmailEventSubscriptionRESTServiceImpl;
import com.hyrax.client.email.api.service.impl.EmailEventSubscriptionRESTClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class ColumnEmailEventSubscriptionConfiguration {

    @Autowired
    private EmailEventRESTClientProperties emailEventRESTClientProperties;

    @Autowired
    private EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    @Bean
    @ConditionalOnMissingBean(name = "columnEmailEventSubscriptionJerseyClient")
    public Client columnEmailEventSubscriptionJerseyClient() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean(name = "columnEmailEventSubscriptionRESTClient")
    public EmailEventSubscriptionRESTClient<ColumnEventSubscriptionRequest> columnEmailEventSubscriptionRESTClient(final Client columnEmailEventSubscriptionJerseyClient) {
        return new EmailEventSubscriptionRESTClient<>(columnEmailEventSubscriptionJerseyClient, emailEventRESTClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ColumnEmailEventSubscriptionRESTService columnEmailEventSubscriptionRESTService(final EmailEventSubscriptionRESTClient<ColumnEventSubscriptionRequest> columnEmailEventSubscriptionRESTClient) {
        return new ColumnEmailEventSubscriptionRESTServiceImpl(columnEmailEventSubscriptionRESTClient, emailEventRESTEndpointProperties);
    }
}
