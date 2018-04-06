package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.TaskEventSubscriptionRequest;
import com.hyrax.client.email.api.service.TaskEmailEventSubscriptionRESTService;
import com.hyrax.client.email.api.service.impl.EmailEventSubscriptionRESTClient;
import com.hyrax.client.email.api.service.impl.TaskEmailEventSubscriptionRESTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class TaskEmailEventSubscriptionConfiguration {

    @Autowired
    private EmailEventSubscriptionRESTClientProperties emailEventSubscriptionRESTClientProperties;

    @Autowired
    private EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    @Bean
    @ConditionalOnMissingBean(name = "taskEmailEventSubscriptionJerseyClient")
    public Client taskEmailEventSubscriptionJerseyClient() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean(name = "taskEmailEventSubscriptionRESTClient")
    public EmailEventSubscriptionRESTClient<TaskEventSubscriptionRequest> taskEmailEventSubscriptionRESTClient(final Client taskEmailEventSubscriptionJerseyClient) {
        return new EmailEventSubscriptionRESTClient<>(taskEmailEventSubscriptionJerseyClient, emailEventSubscriptionRESTClientProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskEmailEventSubscriptionRESTService taskEmailEventSubscriptionRESTService(final EmailEventSubscriptionRESTClient<TaskEventSubscriptionRequest> taskEmailEventSubscriptionRESTClient) {
        return new TaskEmailEventSubscriptionRESTServiceImpl(taskEmailEventSubscriptionRESTClient, emailEventSubscriptionRESTEndpointProperties);
    }
}