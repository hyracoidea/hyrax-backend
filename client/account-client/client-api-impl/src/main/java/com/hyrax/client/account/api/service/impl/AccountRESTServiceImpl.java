package com.hyrax.client.account.api.service.impl;

import com.hyrax.client.account.api.response.HyraxResponse;
import com.hyrax.client.account.api.response.ResponseStatus;
import com.hyrax.client.account.api.response.UsernameWrapperResponse;
import com.hyrax.client.account.api.service.AccountRESTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.Objects;

public class AccountRESTServiceImpl implements AccountRESTService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRESTServiceImpl.class);

    private static final String ERROR_MESSAGE_REST_CALL = "Unexpected exception happened under the REST call";

    private final AccountRESTClient accountRESTClient;

    public AccountRESTServiceImpl(final AccountRESTClient accountRESTClient) {
        this.accountRESTClient = accountRESTClient;
    }

    @Override
    public HyraxResponse<UsernameWrapperResponse> retrieveAllUsernames() {
        HyraxResponse<UsernameWrapperResponse> result;
        Response response = null;

        try {
            response = accountRESTClient.callRetrieveAllUsernamesEndpoint();
            result = processResponse(response);
        } catch (final Exception e) {
            LOGGER.error(ERROR_MESSAGE_REST_CALL, e);
            result = HyraxResponse.<UsernameWrapperResponse>builder()
                    .responseStatus(ResponseStatus.SERVER_ERROR)
                    .reason(ERROR_MESSAGE_REST_CALL)
                    .build();
        } finally {
            if (Objects.nonNull(response)) {
                response.close();
            }
        }
        return result;
    }

    private HyraxResponse<UsernameWrapperResponse> processResponse(final Response response) {
        HyraxResponse<UsernameWrapperResponse> result = null;

        switch (response.getStatusInfo().getFamily()) {
            case SUCCESSFUL:
                result = HyraxResponse.<UsernameWrapperResponse>builder()
                        .responseStatus(ResponseStatus.SUCCESSFUL)
                        .payload(response.readEntity(UsernameWrapperResponse.class))
                        .build();
                break;
            case CLIENT_ERROR:
                result = HyraxResponse.<UsernameWrapperResponse>builder()
                        .responseStatus(ResponseStatus.CLIENT_ERROR)
                        .reason(response.getStatusInfo().getReasonPhrase())
                        .build();
                response.readEntity(String.class);
                break;
            case SERVER_ERROR:
                result = HyraxResponse.<UsernameWrapperResponse>builder()
                        .responseStatus(ResponseStatus.SERVER_ERROR)
                        .reason(response.getStatusInfo().getReasonPhrase())
                        .build();
                response.readEntity(String.class);
                break;
        }

        return result;
    }
}
