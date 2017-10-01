package com.hyrax.client.common.api.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.ZonedDateTime;

@Data
@Builder
@ToString(exclude = {"exceptionMessage", "time"})
@EqualsAndHashCode(exclude = {"exceptionMessage", "time"})
public class ErrorResponse {

    private final String exceptionMessage;
    private final String exceptionType;

    private final String httpStatus;
    private final int httpStatusCode;

    private final ZonedDateTime time;
}
