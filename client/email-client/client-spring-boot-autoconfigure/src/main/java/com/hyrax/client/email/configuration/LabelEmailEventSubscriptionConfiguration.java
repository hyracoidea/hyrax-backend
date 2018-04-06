package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.LabelEventSubscriptionRequest;
import com.hyrax.client.email.api.service.LabelEmailEventSubscriptionRESTService;
import com.hyrax.client.email.api.service.impl.EmailEventSubscriptionRESTClient;
import com.hyrax.client.email.api.service.impl.LabelEmailEventSubscriptionRESTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class LabelEmailEventSubscriptionConfiguration {

    @Autowired
    private EmailEventSubscriptionRESTClientProperties emailEventSubscriptionRESTClientProperties;

    @Autowired
    private EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    @Bean
    @ConditionalOnMissingBean(name = "labelEmailEventSubscriptionJerseyClient")
    public Client labelEmailEventSubscriptionJerseyClient() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean(name = "labelEmailEventSubscriptionRESTClient")
    public EmailEventSubscriptionRESTClient<LabelEventSubscriptionRequest> labelEmailEventSubscriptionRESTClient(final Client labelEmailEventSubscriptionJerseyClient) {
        return new EmailEventSubscriptionRESTClient<>(labelEmailEventSubscriptionJerseyClient, emailEventSubscriptionRESTClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public LabelEmailEventSubscriptionRESTService labelEmailEventSubscriptionRESTService(final EmailEventSubscriptionRESTClient<LabelEventSubscriptionRequest> labelEmailEventSubscriptionRESTClient) {
        return new LabelEmailEventSubscriptionRESTServiceImpl(labelEmailEventSubscriptionRESTClient, emailEventSubscriptionRESTEndpointProperties);
    }
}
