package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.ColumnEventSubscriptionRequest;
import com.hyrax.client.email.api.service.ColumnEmailEventSubscriptionRESTService;

public class ColumnEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<ColumnEventSubscriptionRequest> implements ColumnEmailEventSubscriptionRESTService {

    private final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    public ColumnEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<ColumnEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                       final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventSubscriptionRESTEndpointProperties = emailEventSubscriptionRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventSubscriptionRESTEndpointProperties.getPathToColumn();
    }
}
