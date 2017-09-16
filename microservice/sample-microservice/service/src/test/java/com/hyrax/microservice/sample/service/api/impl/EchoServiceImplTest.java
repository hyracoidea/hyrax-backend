package com.hyrax.microservice.sample.service.api.impl;

import com.hyrax.microservice.sample.data.entity.EchoEntity;
import com.hyrax.microservice.sample.data.mapper.EchoMapper;
import com.hyrax.microservice.sample.service.api.EchoService;
import com.hyrax.microservice.sample.service.domain.Echo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EchoServiceImplTest {

    private static final Long NON_EXISTING_ECHO_ID = -1L;
    private static final Long EXISTING_ECHO_ID = 1L;

    private static final String ECHO_MESSAGE = "Echo message";

    @Mock
    private EchoMapper echoMapper;

    @Mock
    private ModelMapper modelMapper;

    private EchoService echoService;

    @Before
    public void setup() {
        echoService = new EchoServiceImpl(echoMapper, modelMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByEchoIdShouldThrowIllegalArgumentExceptionWhenParameterEchoIdIsNull() {
        // Given

        // When
        echoService.getByEchoId(null);

        // Then
    }

    @Test
    public void getByEchoIdShouldReturnWithEmptyOptionalWhenParameterEchoIdDoesNotExist() {
        // Given
        given(echoMapper.findById(NON_EXISTING_ECHO_ID)).willReturn(null);

        // When
        final Optional<Echo> result = echoService.getByEchoId(NON_EXISTING_ECHO_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), equalTo(false));

        then(echoMapper).should().findById(NON_EXISTING_ECHO_ID);
        verifyNoMoreInteractions(echoMapper, modelMapper);
    }

    @Test
    public void getByEchoIdShouldReturnWithNonEmptyOptionalWhenParameterEchoIdExists() {
        // Given
        final EchoEntity echoEntity = buildEchoEntity();
        final Echo expected = buildEcho();

        given(echoMapper.findById(EXISTING_ECHO_ID)).willReturn(echoEntity);
        given(modelMapper.map(echoEntity, Echo.class)).willReturn(expected);

        // When
        final Optional<Echo> result = echoService.getByEchoId(EXISTING_ECHO_ID);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get(), equalTo(expected));

        then(echoMapper).should().findById(EXISTING_ECHO_ID);
        then(modelMapper).should().map(echoEntity, Echo.class);
        verifyNoMoreInteractions(echoMapper, modelMapper);
    }

    private Echo buildEcho() {
        return Echo.builder()
                .id(EXISTING_ECHO_ID)
                .message(ECHO_MESSAGE)
                .build();
    }

    private EchoEntity buildEchoEntity() {
        return EchoEntity.builder()
                .id(EXISTING_ECHO_ID)
                .message(ECHO_MESSAGE)
                .build();
    }
}
