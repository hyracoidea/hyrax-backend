package com.hyrax.microservice.sample.rest.api.response;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorResponse {

    private final String errorMessage;
    private final String exceptionType;
    private final ZonedDateTime time;
}
