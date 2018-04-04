package com.hyrax.client.account.api.service.impl;

import com.hyrax.client.account.api.properties.AccountRESTClientProperties;
import com.hyrax.client.account.api.request.AccountDetailsRequest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class AccountRESTClient {

    private static final String USERNAME_PATH_VARIABLE = "username";

    private final Client client;
    private final AccountRESTClientProperties accountRESTClientProperties;

    public AccountRESTClient(final Client client, final AccountRESTClientProperties accountRESTClientProperties) {
        this.client = client;
        this.accountRESTClientProperties = accountRESTClientProperties;
    }

    public Response callSingleAccountDetailsRESTEndpoint(final String username) {
        return client.target(accountRESTClientProperties.getServiceUrl())
                .path(accountRESTClientProperties.getSingleAccountDetailsEndpoint())
                .resolveTemplate(USERNAME_PATH_VARIABLE, username)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(accountRESTClientProperties.getHeaderName(), accountRESTClientProperties.getToken())
                .get();
    }

    public Response callBulkAccountDetailsRESTEndpoint(final List<String> usernames) {
        return client.target(accountRESTClientProperties.getServiceUrl())
                .path(accountRESTClientProperties.getBulkAccountDetailsEndpoint())
                .request(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(accountRESTClientProperties.getHeaderName(), accountRESTClientProperties.getToken())
                .post(Entity.entity(AccountDetailsRequest.builder().usernames(usernames).build(), MediaType.APPLICATION_JSON_TYPE));
    }
}
