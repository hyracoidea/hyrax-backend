package com.hyrax.client.account.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HyraxResponse<T> {

    private final ResponseStatus responseStatus;
    private final T payload;
    private final String reason;
}
