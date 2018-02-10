package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.domain.request.AccountRequest;
import com.hyrax.microservice.account.service.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AccountRequestToAccountConverterTest {

    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_HASH = "ABCDEFGH1234";
    private static final String AUTHORITY = "USER";

    @Mock
    private PasswordEncoder passwordEncoder;

    private AccountRequestToAccountConverter accountRequestToAccountConverter;

    @Before
    public void init() {
        accountRequestToAccountConverter = new AccountRequestToAccountConverter(passwordEncoder);
    }

    @Test
    public void convertShouldReturnNullWhenParameterIsNull() {
        // Given

        // When
        final Account result = accountRequestToAccountConverter.convert(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void convertShouldReturnNonNullAccountWhenParameterAccountRequestIsNotNull() {
        // Given
        given(passwordEncoder.encode(PASSWORD)).willReturn(PASSWORD_HASH);

        // When
        final Account result = accountRequestToAccountConverter.convert(buildAccountRequest());

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(buildExpectedAccount()));

        then(passwordEncoder).should().encode(PASSWORD);
        verifyNoMoreInteractions(passwordEncoder);
    }

    private AccountRequest buildAccountRequest() {
        return AccountRequest.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .passwordConfirmation(PASSWORD)
                .build();
    }

    private Account buildExpectedAccount() {
        return Account.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .email(EMAIL)
                .passwordHash(PASSWORD_HASH)
                .authority(AUTHORITY)
                .build();
    }
}
