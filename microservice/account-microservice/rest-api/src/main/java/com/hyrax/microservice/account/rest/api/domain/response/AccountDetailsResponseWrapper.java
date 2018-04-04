package com.hyrax.microservice.account.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDetailsResponseWrapper {

    private final List<AccountDetailsResponse> accountDetailsResponses;
}
