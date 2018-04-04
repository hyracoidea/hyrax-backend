package com.hyrax.microservice.account.rest.api.domain.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDetailsRequest {

    private final List<String> usernames;
}
