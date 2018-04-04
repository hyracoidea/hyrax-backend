package com.hyrax.client.account.api.service.impl;

import com.hyrax.client.account.api.response.AccountDetailsResponse;
import com.hyrax.client.account.api.response.AccountDetailsResponseWrapper;
import com.hyrax.client.account.api.service.AccountRESTService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class AccountRESTServiceImpl implements AccountRESTService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRESTServiceImpl.class);

    private static final String ERROR_MESSAGE_REST_CALL = "Unexpected exception happened under the REST call";

    private final AccountRESTClient accountRESTClient;

    @Override
    public Optional<AccountDetailsResponse> getAccountDetailsResponse(final String username) {
        Response response = null;
        AccountDetailsResponse result = null;

        try {
            response = accountRESTClient.callSingleAccountDetailsRESTEndpoint(username);
            result = processSingleAccountDetailsResponse(response);
        } catch (final Exception e) {
            LOGGER.error(ERROR_MESSAGE_REST_CALL, e);
        } finally {
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<AccountDetailsResponseWrapper> getAccountDetailsResponses(final List<String> usernames) {
        Response response = null;
        AccountDetailsResponseWrapper result = null;

        try {
            response = accountRESTClient.callBulkAccountDetailsRESTEndpoint(usernames);
            result = processBulkAccountDetailsResponse(response);
        } catch (final Exception e) {
            LOGGER.error(ERROR_MESSAGE_REST_CALL, e);
        } finally {
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
        return Optional.ofNullable(result);
    }

    private AccountDetailsResponse processSingleAccountDetailsResponse(final Response response) {
        AccountDetailsResponse result = null;

        if (Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()) {
            result = response.readEntity(AccountDetailsResponse.class);
        } else {
            response.readEntity(String.class);
        }
        return result;
    }

    private AccountDetailsResponseWrapper processBulkAccountDetailsResponse(final Response response) {
        AccountDetailsResponseWrapper result = null;

        if (Response.Status.Family.SUCCESSFUL == response.getStatusInfo().getFamily()) {
            result = response.readEntity(AccountDetailsResponseWrapper.class);
        } else {
            response.readEntity(String.class);
        }
        return result;
    }
}
