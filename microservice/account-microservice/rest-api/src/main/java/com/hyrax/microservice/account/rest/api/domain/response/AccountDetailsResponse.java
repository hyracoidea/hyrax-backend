package com.hyrax.microservice.account.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailsResponse {

    private final String firstName;

    private final String lastName;

    private final String username;

    private final String email;
}
