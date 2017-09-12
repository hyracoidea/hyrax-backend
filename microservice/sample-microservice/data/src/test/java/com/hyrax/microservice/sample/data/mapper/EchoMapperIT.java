package com.hyrax.microservice.sample.data.mapper;

import com.hyrax.microservice.sample.data.annotation.IntegrationTest;
import com.hyrax.microservice.sample.data.entity.EchoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@IntegrationTest
@RunWith(SpringRunner.class)
public class EchoMapperIT {

    @Autowired
    private EchoMapper echoMapper;

    @Test
    public void findByIdShouldReturnNullWhenParameterIdIsNull() {
        // Given

        // When
        final EchoEntity result = echoMapper.findById(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void findByIdShouldReturnNullWhenParameterIdDoesNotExist() {
        // Given

        // When
        final EchoEntity result = echoMapper.findById(-1L);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void findByIdShouldReturnNonNullWhenParameterIdExists() {
        // Given
        final EchoEntity expected = buildExpectedEchoEntity();

        // When
        final EchoEntity result = echoMapper.findById(1L);

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(expected));
    }

    private EchoEntity buildExpectedEchoEntity() {
        return EchoEntity.builder()
                .id(1L)
                .message("Echo test message")
                .build();
    }
}
