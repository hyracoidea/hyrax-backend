package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.properties.EmailEventRESTEndpointProperties;
import com.hyrax.client.email.api.request.TaskEventSubscriptionRequest;
import com.hyrax.client.email.api.service.TaskEmailEventSubscriptionRESTService;

public class TaskEmailEventSubscriptionRESTServiceImpl extends EmailEventSubscriptionRESTServiceImpl<TaskEventSubscriptionRequest> implements TaskEmailEventSubscriptionRESTService {

    private final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties;

    public TaskEmailEventSubscriptionRESTServiceImpl(final EmailEventSubscriptionRESTClient<TaskEventSubscriptionRequest> emailEventSubscriptionRESTClient,
                                                     final EmailEventRESTEndpointProperties emailEventRESTEndpointProperties) {
        super(emailEventSubscriptionRESTClient);
        this.emailEventRESTEndpointProperties = emailEventRESTEndpointProperties;
    }

    @Override
    protected String getPath() {
        return emailEventRESTEndpointProperties.getPathToTask();
    }
}
