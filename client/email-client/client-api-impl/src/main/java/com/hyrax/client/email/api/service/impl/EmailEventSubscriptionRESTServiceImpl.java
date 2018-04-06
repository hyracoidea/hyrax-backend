package com.hyrax.client.email.api.service.impl;

import com.hyrax.client.email.api.request.BaseEventSubscriptionRequest;
import com.hyrax.client.email.api.service.EmailEventSubscriptionRESTService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.Objects;

@AllArgsConstructor
public abstract class EmailEventSubscriptionRESTServiceImpl<T extends BaseEventSubscriptionRequest> implements EmailEventSubscriptionRESTService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamEmailEventSubscriptionRESTServiceImpl.class);

    private static final String ERROR_MESSAGE_REST_CALL = "Unexpected exception happened under the REST call";

    private final EmailEventSubscriptionRESTClient<T> emailEventSubscriptionRESTClient;

    @Override
    public final void createEventSubscription(final String username) {
        updateEventSubscription((T) new BaseEventSubscriptionRequest(username));
    }

    @Override
    public final void updateEventSubscription(final T eventSubscriptionRequest) {
        Response response = null;
        try {
            response = emailEventSubscriptionRESTClient.callEmailEventSubscriptionRESTEndpoint(getPath(), eventSubscriptionRequest);
        } catch (final Exception e) {
            LOGGER.error(ERROR_MESSAGE_REST_CALL, e);
        } finally {
            if (Objects.nonNull(response)) {
                response.readEntity(String.class);
                response.close();
            }
        }
    }

    protected abstract String getPath();
}
