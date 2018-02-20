package com.hyrax.client.account.api.service.impl;

import com.hyrax.client.account.api.properties.AccountRESTClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AccountRESTClient {

    private final Client client;
    private final AccountRESTClientProperties accountRESTClientProperties;

    public AccountRESTClient(final Client client, final AccountRESTClientProperties accountRESTClientProperties) {
        this.client = client;
        this.accountRESTClientProperties = accountRESTClientProperties;
    }

    public Response callRetrieveAllUsernamesEndpoint() {
        return client.target(accountRESTClientProperties.getServiceUrl())
                .path(accountRESTClientProperties.getPath())
                .path(accountRESTClientProperties.getUsernamesEndpoint())
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(accountRESTClientProperties.getHeaderName(), accountRESTClientProperties.getToken())
                .get();
    }
}
