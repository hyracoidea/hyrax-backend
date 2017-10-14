package com.hyrax.microservice.sample.rest.api.controller;

import com.hyrax.client.common.api.response.ErrorResponse;
import com.hyrax.client.sample.api.response.EchoResponse;
import com.hyrax.microservice.sample.service.api.EchoService;
import com.hyrax.microservice.sample.service.domain.Echo;
import com.hyrax.microservice.sample.service.exception.EchoNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EchoRESTControllerTest extends AbstractRESTControllerTest {

    private static final long NON_EXISTING_ECHO_ID = -1L;
    private static final long EXISTING_ECHO_ID = 1L;

    @Mock
    private EchoService echoService;

    @Mock
    private ConversionService conversionService;

    private EchoRESTController echoRESTController;

    @Override
    protected AbstractRESTController getRESTControllerUnderTest() {
        return echoRESTController;
    }

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
    public void handleNotFound() {
        // Given
        final EchoNotFoundException echoNotFoundException = new EchoNotFoundException(EXCEPTION_MESSAGE);
        final ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .exceptionMessage(EXCEPTION_MESSAGE)
                .exceptionType(echoNotFoundException.getClass().getTypeName())
                .httpStatus(HttpStatus.NOT_FOUND.getReasonPhrase())
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .time(ZonedDateTime.now())
                .build();

        // When
        final ResponseEntity<ErrorResponse> result = echoRESTController.handleNotFound(echoNotFoundException);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

        assertErrorResponse(result.getBody(), expectedErrorResponse);
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
}
