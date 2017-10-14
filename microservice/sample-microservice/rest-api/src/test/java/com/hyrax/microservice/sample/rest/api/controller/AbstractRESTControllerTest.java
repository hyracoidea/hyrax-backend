package com.hyrax.microservice.sample.rest.api.controller;

import com.hyrax.client.common.api.response.ErrorResponse;
import org.exparity.hamcrest.date.ZonedDateTimeMatchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

abstract class AbstractRESTControllerTest {

    protected static final String EXCEPTION_MESSAGE = "Test exception message";

    protected static final long PERIOD_IN_SECONDS = 5;

    @Test
    public void handleBadRequest() {
        // Given
        final IllegalArgumentException exception = new IllegalArgumentException(EXCEPTION_MESSAGE);
        final ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .exceptionMessage(EXCEPTION_MESSAGE)
                .exceptionType(exception.getClass().getTypeName())
                .httpStatus(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .time(ZonedDateTime.now())
                .build();

        // When
        final ResponseEntity<ErrorResponse> result = getRESTControllerUnderTest().handleBadRequest(exception);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        assertErrorResponse(result.getBody(), expectedErrorResponse);
    }

    @Test
    public void handleInternalServerError() {
        // Given
        final Exception exception = new Exception(EXCEPTION_MESSAGE);
        final ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .exceptionMessage(EXCEPTION_MESSAGE)
                .exceptionType(exception.getClass().getTypeName())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .time(ZonedDateTime.now())
                .build();

        // When
        final ResponseEntity<ErrorResponse> result = getRESTControllerUnderTest().handleInternalServerError(exception);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

        assertErrorResponse(result.getBody(), expectedErrorResponse);
    }

    protected abstract AbstractRESTController getRESTControllerUnderTest();

    protected void assertErrorResponse(final ErrorResponse actual, final ErrorResponse expected) {
        assertThat(actual, notNullValue());
        assertThat(expected, notNullValue());

        assertThat(actual.getExceptionMessage(), equalTo(expected.getExceptionMessage()));
        assertThat(actual.getExceptionType(), equalTo(expected.getExceptionType()));

        assertThat(actual.getHttpStatus(), equalTo(expected.getHttpStatus()));
        assertThat(actual.getHttpStatusCode(), equalTo(expected.getHttpStatusCode()));

        assertThat(actual.getTime(), ZonedDateTimeMatchers.within(PERIOD_IN_SECONDS, ChronoUnit.SECONDS, expected.getTime()));
    }
}
