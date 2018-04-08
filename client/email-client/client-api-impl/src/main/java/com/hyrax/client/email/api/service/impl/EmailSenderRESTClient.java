package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTClientProperties;
import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.EmailNotificationRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@AllArgsConstructor
public class EmailSenderRESTClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderRESTClient.class);

    private final Client client;

    private final EmailEventRESTClientProperties emailEventRESTClientProperties;

    private final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    public Response callEmailSenderRESTEndpoint(final EmailNotificationRequest emailNotificationRequest) {
        LOGGER.info("Target: {} with request: {}", emailEventRESTClientProperties.getServiceUrl() + emailEventRESTEndpointProperties.getPathToSendEmail(), emailNotificationRequest);
        return client.target(emailEventRESTClientProperties.getServiceUrl())
                .path(emailEventRESTEndpointProperties.getPathToSendEmail())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(emailEventRESTClientProperties.getHeaderName(), emailEventRESTClientProperties.getToken())
                .post(Entity.entity(emailNotificationRequest, MediaType.APPLICATION_JSON_TYPE));
    }
}
