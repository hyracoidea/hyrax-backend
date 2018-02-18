package com.hyrax.microservice.account.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsernameWrapperResponse {

    private final List<String> usernames;
}
