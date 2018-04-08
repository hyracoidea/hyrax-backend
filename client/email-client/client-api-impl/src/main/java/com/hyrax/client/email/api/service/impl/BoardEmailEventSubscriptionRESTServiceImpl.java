package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.BoardEventSubscriptionRequest;
import com.hyrax.client.email.api.service.BoardEmailEventSubscriptionRESTService;

public class BoardEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<BoardEventSubscriptionRequest> implements BoardEmailEventSubscriptionRESTService {

    private final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    public BoardEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<BoardEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                      final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventRESTEndpointProperties = emailEventRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventRESTEndpointProperties.getPathToBoard();
    }
}
