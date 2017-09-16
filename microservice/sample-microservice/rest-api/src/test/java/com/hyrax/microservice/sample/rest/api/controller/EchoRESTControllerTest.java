package com.hyrax.microservice.sample.rest.api.controller;

import com.hyrax.microservice.sample.rest.api.response.EchoResponse;
import com.hyrax.microservice.sample.rest.api.response.ErrorResponse;
import com.hyrax.microservice.sample.service.api.EchoService;
import com.hyrax.microservice.sample.service.domain.Echo;
import com.hyrax.microservice.sample.service.exception.EchoNotFoundException;
import org.exparity.hamcrest.date.ZonedDateTimeMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EchoRESTControllerTest {

    private static final long NON_EXISTING_ECHO_ID = -1L;
    private static final long EXISTING_ECHO_ID = 1L;

    private static final String EXCEPTION_MESSAGE = "Test exception message";

    private static final long PERIOD_IN_SECONDS = 5;

    @Mock
    private EchoService echoService;

    @Mock
    private ConversionService conversionService;

    private EchoRESTController echoRESTController;

    @Before
    public void setup() {
        echoRESTController = new EchoRESTController(echoService, conversionService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieveEchoResponseShouldThrowIllegalArgumentExceptionWhenParameterEchoIdIsNull() {
        // Given
        given(echoService.getByEchoId(null)).willThrow(IllegalArgumentException.class);

        // When
        echoRESTController.retrieveEchoResponse(null);

        // Then
    }

    @Test(expected = EchoNotFoundException.class)
    public void retrieveEchoResponseShouldThrowEchoNotFoundExceptionWhenParameterEchoIdDoesNotExist() {
        // Given
        given(echoService.getByEchoId(NON_EXISTING_ECHO_ID)).willReturn(Optional.empty());

        // When
        echoRESTController.retrieveEchoResponse(NON_EXISTING_ECHO_ID);

        // Then
    }

    @Test
    public void retrieveEchoResponseShouldReturnWithExistingEchoResponseWhenParameterEchoIdExists() {
        // Given
        final Echo echo = buildEcho(EXISTING_ECHO_ID);
        final EchoResponse echoResponse = buildEchoResponse(EXISTING_ECHO_ID);

        given(echoService.getByEchoId(EXISTING_ECHO_ID)).willReturn(Optional.of(echo));
        given(conversionService.convert(echo, EchoResponse.class)).willReturn(echoResponse);

        // When
        final ResponseEntity<EchoResponse> result = echoRESTController.retrieveEchoResponse(EXISTING_ECHO_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(result.getBody(), equalTo(echoResponse));

        then(echoService).should().getByEchoId(EXISTING_ECHO_ID);
        then(conversionService).should().convert(echo, EchoResponse.class);
        verifyNoMoreInteractions(echoService, conversionService);
    }

    @Test
    public void handleResourceNotFoundException() {
        // Given
        final EchoNotFoundException echoNotFoundException = new EchoNotFoundException(EXCEPTION_MESSAGE);

        // When
        final ResponseEntity<ErrorResponse> result = echoRESTController.handleResourceNotFoundException(echoNotFoundException);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        assertErrorResponse(result.getBody(), buildErrorResponse(EXCEPTION_MESSAGE, echoNotFoundException.getClass().getTypeName(), ZonedDateTime.now()));
    }

    @Test
    public void handleGeneralServerException() {
        // Given
        final Exception exception = new Exception(EXCEPTION_MESSAGE);

        // When
        final ResponseEntity<ErrorResponse> result = echoRESTController.handleGeneralServerException(exception);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

        assertErrorResponse(result.getBody(), buildErrorResponse(EXCEPTION_MESSAGE, exception.getClass().getTypeName(), ZonedDateTime.now()));
    }

    private void assertErrorResponse(final ErrorResponse actual, final ErrorResponse expected) {
        assertThat(actual, notNullValue());
        assertThat(expected, notNullValue());

        assertThat(actual.getErrorMessage(), equalTo(expected.getErrorMessage()));
        assertThat(actual.getExceptionType(), equalTo(expected.getExceptionType()));
        assertThat(actual.getTime(), ZonedDateTimeMatchers.within(PERIOD_IN_SECONDS, ChronoUnit.SECONDS, expected.getTime()));
    }

    private Echo buildEcho(final long echoId) {
        return Echo.builder()
                .id(echoId)
                .build();
    }

    private EchoResponse buildEchoResponse(final long echoId) {
        return EchoResponse.builder()
                .id(echoId)
                .build();
    }

    private ErrorResponse buildErrorResponse(final String errorMessage, final String exceptionType, final ZonedDateTime time) {
        return ErrorResponse.builder()
                .errorMessage(errorMessage)
                .exceptionType(exceptionType)
                .time(time)
                .build();
    }
}
