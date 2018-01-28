package com.hyrax.microservice.account.rest.api.validation.bindingresult;

import com.hyrax.microservice.account.rest.api.response.RequestValidationDetail;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class BindingResultProcessorTest {

    private static final String OBJECT_NAME = "ObjectName";
    private static final String FIELD_NAME = "fieldName";
    private static final String CODE = "AnnotationName";
    private static final String DEFAULT_ERROR_MESSAGE = "defaultErrorMessage";

    private final FieldError fieldError = new FieldError(OBJECT_NAME, FIELD_NAME, DEFAULT_ERROR_MESSAGE);
    private final ObjectError globalError = new ObjectError(OBJECT_NAME, new String[]{CODE}, null, DEFAULT_ERROR_MESSAGE);


    @Mock
    private ConversionService conversionService;

    @Mock
    private BindingResult bindingResult;

    private BindingResultProcessor bindingResultProcessor;

    @Before
    public void init() {
        bindingResultProcessor = new BindingResultProcessor(conversionService);
    }

    @Test
    public void processShouldReturnEmptyListWhenThereIsNoValidationErrors() {
        // Given
        given(bindingResult.hasGlobalErrors()).willReturn(false);
        given(bindingResult.hasFieldErrors()).willReturn(false);

        // When
        final ProcessedBindingResult result = bindingResultProcessor.process(bindingResult);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.hasValidationErrors(), is(false));
        assertThat(result.getRequestValidationDetails(), notNullValue());
        assertThat(result.getRequestValidationDetails().isEmpty(), is(true));

        then(bindingResult).should().hasGlobalErrors();
        then(bindingResult).should().hasFieldErrors();
        verifyNoMoreInteractions(bindingResult, conversionService);
    }

    @Test
    public void processShouldReturnSingleElementWhenThereIsOneGlobalError() {
        // Given
        final List<RequestValidationDetail> expectedRequestValidationDetails = Lists.newArrayList(
                buildRequestValidationForGlobalError()
        );

        given(bindingResult.hasGlobalErrors()).willReturn(true);
        given(bindingResult.hasFieldErrors()).willReturn(false);
        given(bindingResult.getGlobalErrors()).willReturn(Lists.newArrayList(globalError));
        given(conversionService.convert(globalError, RequestValidationDetail.class)).willReturn(buildRequestValidationForGlobalError());

        // When
        final ProcessedBindingResult result = bindingResultProcessor.process(bindingResult);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.hasValidationErrors(), is(true));
        assertThat(result.getRequestValidationDetails(), equalTo(expectedRequestValidationDetails));

        then(bindingResult).should().hasGlobalErrors();
        then(bindingResult).should().hasFieldErrors();
        then(bindingResult).should().getGlobalErrors();
        then(conversionService).should().convert(globalError, RequestValidationDetail.class);
        verifyNoMoreInteractions(bindingResult, conversionService);
    }

    @Test
    public void processShouldReturnSingleElementWhenThereIsOneFieldError() {
        // Given
        final List<RequestValidationDetail> expectedRequestValidationDetails = Lists.newArrayList(
                buildRequestValidationForFieldError()
        );

        given(bindingResult.hasGlobalErrors()).willReturn(false);
        given(bindingResult.hasFieldErrors()).willReturn(true);
        given(bindingResult.getFieldErrors()).willReturn(Lists.newArrayList(fieldError));
        given(conversionService.convert(fieldError, RequestValidationDetail.class)).willReturn(buildRequestValidationForFieldError());

        // When
        final ProcessedBindingResult result = bindingResultProcessor.process(bindingResult);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.hasValidationErrors(), is(true));
        assertThat(result.getRequestValidationDetails(), equalTo(expectedRequestValidationDetails));

        then(bindingResult).should().hasGlobalErrors();
        then(bindingResult).should().hasFieldErrors();
        then(bindingResult).should().getFieldErrors();
        then(conversionService).should().convert(fieldError, RequestValidationDetail.class);
        verifyNoMoreInteractions(bindingResult, conversionService);
    }

    @Test
    public void processShouldReturnTwoElementsWhenThereIsOneGlobalErrorAndOneFieldError() {
        // Given
        final List<RequestValidationDetail> expectedRequestValidationDetails = Lists.newArrayList(
                buildRequestValidationForGlobalError(), buildRequestValidationForFieldError()
        );

        given(bindingResult.hasGlobalErrors()).willReturn(true);
        given(bindingResult.hasFieldErrors()).willReturn(true);
        given(bindingResult.getGlobalErrors()).willReturn(Lists.newArrayList(globalError));
        given(bindingResult.getFieldErrors()).willReturn(Lists.newArrayList(fieldError));
        given(conversionService.convert(globalError, RequestValidationDetail.class)).willReturn(buildRequestValidationForGlobalError());
        given(conversionService.convert(fieldError, RequestValidationDetail.class)).willReturn(buildRequestValidationForFieldError());

        // When
        final ProcessedBindingResult result = bindingResultProcessor.process(bindingResult);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.hasValidationErrors(), is(true));
        assertThat(result.getRequestValidationDetails(), equalTo(expectedRequestValidationDetails));

        then(bindingResult).should().hasGlobalErrors();
        then(bindingResult).should().hasFieldErrors();
        then(bindingResult).should().getGlobalErrors();
        then(bindingResult).should().getFieldErrors();
        then(conversionService).should().convert(fieldError, RequestValidationDetail.class);
        then(conversionService).should().convert(globalError, RequestValidationDetail.class);
        verifyNoMoreInteractions(bindingResult, conversionService);
    }

    @Test
    public void processShouldReturnFourElementsWhenThereAreTwoGlobalErrorsAndTwoFieldErrors() {
        // Given
        final List<RequestValidationDetail> requestValidationDetails = Lists.newArrayList(
                buildRequestValidationForGlobalError(), buildRequestValidationForGlobalError(), buildRequestValidationForFieldError(), buildRequestValidationForFieldError()
        );

        given(bindingResult.hasGlobalErrors()).willReturn(true);
        given(bindingResult.hasFieldErrors()).willReturn(true);
        given(bindingResult.getGlobalErrors()).willReturn(Collections.nCopies(2, globalError));
        given(bindingResult.getFieldErrors()).willReturn(Collections.nCopies(2, fieldError));
        given(conversionService.convert(globalError, RequestValidationDetail.class)).willReturn(buildRequestValidationForGlobalError());
        given(conversionService.convert(fieldError, RequestValidationDetail.class)).willReturn(buildRequestValidationForFieldError());

        // When
        final ProcessedBindingResult result = bindingResultProcessor.process(bindingResult);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.hasValidationErrors(), is(true));
        assertThat(result.getRequestValidationDetails(), equalTo(requestValidationDetails));

        then(bindingResult).should().hasGlobalErrors();
        then(bindingResult).should().hasFieldErrors();
        then(bindingResult).should().getGlobalErrors();
        then(bindingResult).should().getFieldErrors();
        then(conversionService).should(times(2)).convert(fieldError, RequestValidationDetail.class);
        then(conversionService).should(times(2)).convert(globalError, RequestValidationDetail.class);
        verifyNoMoreInteractions(bindingResult, conversionService);
    }


    private RequestValidationDetail buildRequestValidationForGlobalError() {
        return RequestValidationDetail.builder()
                .field(CODE)
                .message(DEFAULT_ERROR_MESSAGE)
                .build();
    }

    private RequestValidationDetail buildRequestValidationForFieldError() {
        return RequestValidationDetail.builder()
                .field(FIELD_NAME)
                .message(DEFAULT_ERROR_MESSAGE)
                .build();
    }
}
