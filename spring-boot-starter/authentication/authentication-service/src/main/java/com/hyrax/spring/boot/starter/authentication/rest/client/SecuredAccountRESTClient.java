package com.hyrax.spring.boot.starter.authentication.rest.client;

import javax.ws.rs.core.Response;

public interface SecuredAccountRESTClient {

    Response callRetrieveSecuredAccountEndpoint(String username);
}
