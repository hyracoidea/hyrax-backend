package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.TeamEventSubscriptionRequest;
import com.hyrax.client.email.api.service.TeamEmailEventSubscriptionRESTService;

public class TeamEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<TeamEventSubscriptionRequest> implements TeamEmailEventSubscriptionRESTService {

    private final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    public TeamEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<TeamEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                     final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventRESTEndpointProperties = emailEventRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventRESTEndpointProperties.getPathToTeam();
    }
}
