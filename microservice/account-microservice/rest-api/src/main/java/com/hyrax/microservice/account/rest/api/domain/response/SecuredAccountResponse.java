package com.hyrax.microservice.account.rest.api.domain.response;

import com.hyrax.microservice.account.rest.api.domain.Authority;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecuredAccountResponse {

    private final String username;

    private final String password;

    private final Authority authority;
}
