package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.LabelEventSubscriptionRequest;
import com.hyrax.client.email.api.service.LabelEmailEventSubscriptionRESTService;

public class LabelEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<LabelEventSubscriptionRequest> implements LabelEmailEventSubscriptionRESTService {

    private final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    public LabelEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<LabelEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                      final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventRESTEndpointProperties = emailEventRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventRESTEndpointProperties.getPathToLabel();
    }
}
