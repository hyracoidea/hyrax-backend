package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTClientProperties;
import com.hyrax.client.email.api.request.BaseEventSubscriptionRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@AllArgsConstructor
public class EmailEventSubscriptionRESTClient<T extends BaseEventSubscriptionRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailEventSubscriptionRESTClient.class);

    private final Client client;

    private final EmailEventRESTClientProperties emailEventRESTClientProperties;

    public Response callEmailEventSubscriptionRESTEndpoint(final String path, final T request) {
        LOGGER.info("Target: {} with request: {}", emailEventRESTClientProperties.getServiceUrl() + path, request);
        return client.target(emailEventRESTClientProperties.getServiceUrl())
                .path(path)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(emailEventRESTClientProperties.getHeaderName(), emailEventRESTClientProperties.getToken())
                .put(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
    }
}
