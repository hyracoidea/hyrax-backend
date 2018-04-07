package com.hyrax.microservice.email.rest.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private final String message;
}
