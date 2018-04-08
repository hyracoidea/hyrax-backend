package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.ColumnEventSubscriptionRequest;
import com.hyrax.client.email.api.service.ColumnEmailEventSubscriptionRESTService;

public class ColumnEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<ColumnEventSubscriptionRequest> implements ColumnEmailEventSubscriptionRESTService {

    private final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    public ColumnEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<ColumnEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                       final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventRESTEndpointProperties = emailEventRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventRESTEndpointProperties.getPathToColumn();
    }
}
