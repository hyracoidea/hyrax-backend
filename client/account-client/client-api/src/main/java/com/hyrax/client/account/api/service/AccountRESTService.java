package com.hyrax.client.account.api.service;

import com.hyrax.client.account.api.response.HyraxResponse;
import com.hyrax.client.account.api.response.UsernameWrapperResponse;

public interface AccountRESTService {

    HyraxResponse<UsernameWrapperResponse> retrieveAllUsernames();
}
