package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventSubscriptionRESTEndpointProperties;
import com.hyrax.client.email.api.request.TaskEventSubscriptionRequest;
import com.hyrax.client.email.api.service.TaskEmailEventSubscriptionRESTService;

public class TaskEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<TaskEventSubscriptionRequest> implements TaskEmailEventSubscriptionRESTService {

    private final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties;

    public TaskEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<TaskEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                     final EmailEventSubscriptionRESTEndpointProperties emailEventSubscriptionRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventSubscriptionRESTEndpointProperties = emailEventSubscriptionRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventSubscriptionRESTEndpointProperties.getPathToTask();
    }
}
