package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.LabelEventSubscriptionRequest;
import com.hyrax.client.email.api.service.LabelEmailEventSubscriptionRESTService;

public class LabelEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<LabelEventSubscriptionRequest> implements LabelEmailEventSubscriptionRESTService {

    private final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    public LabelEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<LabelEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                      final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventSubscriptionRESTEndpointProperties = emailEventSubscriptionRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventSubscriptionRESTEndpointProperties.getPathToLabel();
    }
}
