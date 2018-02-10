package com.hyrax.microservice.account.rest.api.controller;

import com.hyrax.microservice.account.rest.api.exception.RequestValidationException;
import com.hyrax.microservice.account.rest.api.domain.request.AccountRequest;
import com.hyrax.microservice.account.rest.api.domain.response.ErrorResponse;
import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationDetail;
import com.hyrax.microservice.account.rest.api.domain.response.RequestValidationResponse;
import com.hyrax.microservice.account.rest.api.validation.bindingresult.BindingResultProcessor;
import com.hyrax.microservice.account.rest.api.validation.bindingresult.ProcessedBindingResult;
import com.hyrax.microservice.account.service.api.AccountService;
import com.hyrax.microservice.account.service.domain.Account;
import com.hyrax.microservice.account.service.exception.AccountAlreadyExistsException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AccountRESTControllerTest {

    private static final String EMAIL = "email@email.com";
    private static final String USERNAME = "username";

    private static final String TEST_EXCEPTION_MESSAGE = "Test exception message";
    private static final String EMAIL_OR_USERNAME_ALREADY_EXIST_EXCEPTION_MESSAGE = String.format("Account already exists with this username=%s or this email=%s", USERNAME, EMAIL);

    private final AccountRequest accountRequest = AccountRequest.builder()
            .username(USERNAME)
            .email(EMAIL)
            .build();
    private final Account account = Account.builder()
            .username(USERNAME)
            .email(EMAIL)
            .build();

    @Mock
    private AccountService accountService;

    @Mock
    private ConversionService conversionService;

    @Mock
    private BindingResultProcessor bindingResultProcessor;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ProcessedBindingResult processedBindingResult;

    private AccountRESTController accountRESTController;

    @Before
    public void init() {
        accountRESTController = new AccountRESTController(accountService, conversionService, bindingResultProcessor);
    }

    @Test(expected = RequestValidationException.class)
    public void createAccountShouldThrowRequestValidationExceptionWhenParameterAccountRequestIsNotValid() {
        // Given
        final AccountRequest accountRequest = AccountRequest.builder().build();

        given(bindingResultProcessor.process(bindingResult)).willReturn(processedBindingResult);
        given(processedBindingResult.hasValidationErrors()).willReturn(true);

        // When
        accountRESTController.createAccount(accountRequest, bindingResult);

        // Then
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void createAccountShouldThrowEmailAlreadyExistsExceptionWhenEmailAlreadyExists() {
        // Given
        final AccountAlreadyExistsException accountAlreadyExistsException = new AccountAlreadyExistsException(EMAIL_OR_USERNAME_ALREADY_EXIST_EXCEPTION_MESSAGE);

        given(bindingResultProcessor.process(bindingResult)).willReturn(processedBindingResult);
        given(processedBindingResult.hasValidationErrors()).willReturn(false);
        given(conversionService.convert(accountRequest, Account.class)).willReturn(account);
        doThrow(accountAlreadyExistsException).when(accountService).saveAccount(account);

        // When
        accountRESTController.createAccount(accountRequest, bindingResult);

        // Then
    }

    @Test
    public void createAccountShouldReturnHttpStatusNoContent() {
        // Given
        given(bindingResultProcessor.process(bindingResult)).willReturn(processedBindingResult);
        given(processedBindingResult.hasValidationErrors()).willReturn(false);
        given(conversionService.convert(accountRequest, Account.class)).willReturn(account);
        doNothing().when(accountService).saveAccount(account);

        // When
        final ResponseEntity<Void> response = accountRESTController.createAccount(accountRequest, bindingResult);

        // Then
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

        then(bindingResultProcessor).should().process(bindingResult);
        then(processedBindingResult).should().hasValidationErrors();
        then(conversionService).should().convert(accountRequest, Account.class);
        then(accountService).should().saveAccount(account);
        verifyNoMoreInteractions(bindingResultProcessor, conversionService, accountService);
    }

    @Test
    public void handleRequestValidationException() {
        // Given
        final List<RequestValidationDetail> requestValidationDetails = Lists.newArrayList(RequestValidationDetail.builder().build());

        given(processedBindingResult.getRequestValidationDetails()).willReturn(requestValidationDetails);

        // When
        final ResponseEntity<RequestValidationResponse> response = accountRESTController.handleRequestValidationException(new RequestValidationException(processedBindingResult));

        // Then
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNPROCESSABLE_ENTITY));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getRequestValidationDetails(), equalTo(requestValidationDetails));
    }

    @Test
    public void handleAccountAlreadyExistsException() {
        // Given

        // When
        final ResponseEntity<ErrorResponse> response = accountRESTController.handleAccountAlreadyExistsException(new AccountAlreadyExistsException(EMAIL_OR_USERNAME_ALREADY_EXIST_EXCEPTION_MESSAGE));

        // Then
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CONFLICT));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getMessage(), equalTo(EMAIL_OR_USERNAME_ALREADY_EXIST_EXCEPTION_MESSAGE));
    }

    @Test
    public void handleHttpMessageNotReadableException() {
        // Given

        // When
        final ResponseEntity<Void> response = accountRESTController.handleHttpMessageNotReadableException(new HttpMessageNotReadableException(TEST_EXCEPTION_MESSAGE));

        // Then
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleGeneralServerException() {
        // Given

        // When
        final ResponseEntity<Void> response = accountRESTController.handleGeneralServerException(new Exception());

        // Then
        assertThat(response, notNullValue());
        assertThat(response.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
