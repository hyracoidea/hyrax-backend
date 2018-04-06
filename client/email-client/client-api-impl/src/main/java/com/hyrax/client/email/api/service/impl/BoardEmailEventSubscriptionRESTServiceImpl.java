package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.BoardEventSubscriptionRequest;
import com.hyrax.client.email.api.service.BoardEmailEventSubscriptionRESTService;

public class BoardEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<BoardEventSubscriptionRequest> implements BoardEmailEventSubscriptionRESTService {

    private final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    public BoardEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<BoardEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                      final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventSubscriptionRESTEndpointProperties = emailEventSubscriptionRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventSubscriptionRESTEndpointProperties.getPathToBoard();
    }
}
