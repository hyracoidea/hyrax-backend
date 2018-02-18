package com.hyrax.client.account.api.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsernameWrapperResponse {

    private final List<String> usernames;
}
