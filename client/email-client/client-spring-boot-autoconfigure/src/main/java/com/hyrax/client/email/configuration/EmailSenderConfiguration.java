package com.hyrax.client.email.configuration;

import com.hyrax.client.email.api.properties.EmailEventRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.service.EmailSenderRESTService;
import com.hyrax.client.email.api.service.impl.EmailSenderRESTClient;
import com.hyrax.client.email.api.service.impl.EmailSenderRESTServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
public class EmailSenderConfiguration {

    @Autowired
    private EmailEventRESTClientProperties emailEventRESTClientProperties;

    @Autowired
    private EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    @Bean
    @ConditionalOnMissingBean(name = "emailSenderJerseyClient")
    public Client emailSenderJerseyClient() {
        return ClientBuilder.newClient();
    }

    @Bean
    @ConditionalOnMissingBean(name = "emailSenderRESTClient")
    public EmailSenderRESTClient emailSenderRESTClient(final Client emailSenderJerseyClient) {
        return new EmailSenderRESTClient(emailSenderJerseyClient, emailEventRESTClientProperties, emailEventRESTEndpointProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public EmailSenderRESTService emailSenderRESTService(final EmailSenderRESTClient emailSenderRESTClient) {
        return new EmailSenderRESTServiceImpl(emailSenderRESTClient);
    }
}
