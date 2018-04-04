package com.hyrax.client.account.api.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDetailsRequest {

    private final List<String> usernames;
}
