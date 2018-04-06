package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.TeamEventSubscriptionRequest;
import com.hyrax.client.email.api.service.TeamEmailEventSubscriptionRESTService;

public class TeamEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<TeamEventSubscriptionRequest> implements TeamEmailEventSubscriptionRESTService {

    private final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    public TeamEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<TeamEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                     final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventSubscriptionRESTEndpointProperties = emailEventSubscriptionRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventSubscriptionRESTEndpointProperties.getPathToTeam();
    }
}
