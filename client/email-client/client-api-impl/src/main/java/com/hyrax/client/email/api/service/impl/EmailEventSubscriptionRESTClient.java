package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTClientProperties;
import com.hyrax.client.email.api.request.BaseEventSubscriptionRequest;
import lombok.AllArgsConstructor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@AllArgsConstructor
public class EmailEventSubscriptionRESTClient<T extends BaseEventSubscriptionRequest> {

    private final Client client;

    private final EmailEventSubscriptionRESTClientProperties emailEventSubscriptionRESTClientProperties;

    public Response callEmailEventSubscriptionRESTEndpoint(final String path, final T request) {
        return client.target(emailEventSubscriptionRESTClientProperties.getServiceUrl())
                .path(path)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(emailEventSubscriptionRESTClientProperties.getHeaderName(), emailEventSubscriptionRESTClientProperties.getToken())
                .put(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
    }
}
