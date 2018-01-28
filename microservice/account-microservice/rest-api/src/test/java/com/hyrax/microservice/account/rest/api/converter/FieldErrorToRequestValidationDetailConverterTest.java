package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.response.RequestValidationDetail;
import org.junit.Test;
import org.springframework.validation.FieldError;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FieldErrorToRequestValidationDetailConverterTest {

    private static final String OBJECT_NAME = "ObjectName";
    private static final String FIELD = "field";
    private static final String DEFAULT_ERROR_MESSAGE = "defaultErrorMessage";

    private FieldErrorToRequestValidationDetailConverter converter = new FieldErrorToRequestValidationDetailConverter();

    @Test
    public void convertShouldReturnNullWhenParameterIsNull() {
        // Given

        // When
        final RequestValidationDetail result = converter.convert(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void convertShouldReturnNonNullRequestValidationDetailWhenParameterFieldErrorIsNotNull() {
        // Given
        final FieldError fieldError = new FieldError(OBJECT_NAME, FIELD, DEFAULT_ERROR_MESSAGE);

        // When
        final RequestValidationDetail result = converter.convert(fieldError);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getField(), equalTo(FIELD));
        assertThat(result.getMessage(), equalTo(DEFAULT_ERROR_MESSAGE));
    }
}
