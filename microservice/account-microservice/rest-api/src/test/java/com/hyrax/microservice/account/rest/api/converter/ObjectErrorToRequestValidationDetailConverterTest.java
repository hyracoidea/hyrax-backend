package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationDetail;
import org.junit.Test;
import org.springframework.validation.ObjectError;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ObjectErrorToRequestValidationDetailConverterTest {

    private static final String OBJECT_NAME = "ObjectName";
    private static final String CODE = "AnnotationName";
    private static final String UNCAPITALIZED_CODE = "annotationName";
    private static final String DEFAULT_ERROR_MESSAGE = "defaultErrorMessage";

    private ObjectErrorToRequestValidationDetailConverter converter = new ObjectErrorToRequestValidationDetailConverter();

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
        final ObjectError fieldError = new ObjectError(OBJECT_NAME, new String[]{CODE}, null, DEFAULT_ERROR_MESSAGE);

        // When
        final RequestValidationDetail result = converter.convert(fieldError);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.getField(), equalTo(UNCAPITALIZED_CODE));
        assertThat(result.getMessage(), equalTo(DEFAULT_ERROR_MESSAGE));
    }
}
