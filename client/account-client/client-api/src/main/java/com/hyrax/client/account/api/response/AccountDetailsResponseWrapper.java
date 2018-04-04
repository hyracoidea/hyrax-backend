package com.hyrax.client.account.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDetailsResponseWrapper {

    private final List<AccountDetailsResponse> accountDetailsResponses;
}
