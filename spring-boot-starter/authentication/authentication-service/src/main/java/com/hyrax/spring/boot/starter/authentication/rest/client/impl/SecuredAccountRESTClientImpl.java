package com.hyrax.spring.boot.starter.authentication.rest.client.impl;

import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationAdminProperties;
import com.hyrax.spring.boot.starter.authentication.properties.AuthenticationProperties;
import com.hyrax.spring.boot.starter.authentication.rest.client.SecuredAccountRESTClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SecuredAccountRESTClientImpl implements SecuredAccountRESTClient {

    private static final String PATH_VARIABLE_USERNAME = "username";
    private static final String ACCOUNT_SERVICE_URL = "http://account_microservice:8081/hyrax/api";
    private static final String SECURED_ACCOUNT_ENDPOINT_PATH = "/admin/account/{username}";

    private final Client client;
    private final AuthenticationProperties authenticationProperties;
    private final AuthenticationAdminProperties authenticationAdminProperties;

    public SecuredAccountRESTClientImpl(final Client client, final AuthenticationProperties authenticationProperties,
                                        final AuthenticationAdminProperties authenticationAdminProperties) {
        this.client = client;
        this.authenticationProperties = authenticationProperties;
        this.authenticationAdminProperties = authenticationAdminProperties;
    }

    @Override
    public Response callRetrieveSecuredAccountEndpoint(final String username) {
        return client.target(ACCOUNT_SERVICE_URL)
                .path(SECURED_ACCOUNT_ENDPOINT_PATH)
                .resolveTemplate(PATH_VARIABLE_USERNAME, username)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header(authenticationProperties.getHeaderName(), authenticationAdminProperties.getToken())
                .get();
    }
}
