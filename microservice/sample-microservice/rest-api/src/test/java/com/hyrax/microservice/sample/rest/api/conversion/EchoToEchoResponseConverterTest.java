package com.hyrax.microservice.sample.rest.api.conversion;

import com.hyrax.microservice.sample.rest.api.response.EchoResponse;
import com.hyrax.microservice.sample.service.domain.Echo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EchoToEchoResponseConverterTest {

    private static final Long ECHO_ID = 1L;
    private static final String ECHO_MESSAGE = "Echo message";

    private final EchoToEchoResponseConverter converter = new EchoToEchoResponseConverter();

    @Test
    public void convertShouldReturnWithNullEchoResponseWhenParameterEchoIsNull() {
        // Given

        // When
        final EchoResponse result = converter.convert(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void convertShouldReturnWithNonNullEchoResponseWhenParameterEchoIsNotNull() {
        // Given

        // When
        final EchoResponse result = converter.convert(buildEcho());

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(buildEchoResponse()));
    }

    private Echo buildEcho() {
        return Echo.builder()
                .id(ECHO_ID)
                .message(ECHO_MESSAGE)
                .build();
    }

    private EchoResponse buildEchoResponse() {
        return EchoResponse.builder()
                .id(ECHO_ID)
                .message(ECHO_MESSAGE)
                .build();
    }
}
