package com.hyrax.client.account.api.service;

import com.hyrax.client.account.api.response.AccountDetailsResponse;
import com.hyrax.client.account.api.response.AccountDetailsResponseWrapper;

import java.util.List;
import java.util.Optional;

public interface AccountRESTService {

    Optional<AccountDetailsResponse> getAccountDetailsResponse(String username);

    Optional<AccountDetailsResponseWrapper> getAccountDetailsResponses(List<String> usernames);
}
