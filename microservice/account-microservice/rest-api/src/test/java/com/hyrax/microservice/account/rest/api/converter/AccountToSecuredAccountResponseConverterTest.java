package com.hyrax.microservice.account.rest.api.converter;

import com.hyrax.microservice.account.rest.api.domain.Authority;
import com.hyrax.microservice.account.rest.api.domain.response.SecuredAccountResponse;
import com.hyrax.microservice.account.service.domain.Account;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AccountToSecuredAccountResponseConverterTest {

    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD_HASH = "ABCDEFGH1234";
    private static final String AUTHORITY_AS_STRING = "USER";

    private final AccountToSecuredAccountResponseConverter converter = new AccountToSecuredAccountResponseConverter();

    @Test
    public void convertShouldReturnNullWhenParameterAccountIsNull() {
        // Given

        // When
        final SecuredAccountResponse result = converter.convert(null);

        // Then
        assertThat(result, nullValue());
    }

    @Test
    public void convertShouldReturnNonNullSecuredAccountResponseWhenParameterAccountIsNotNull() {
        // Given
        final SecuredAccountResponse expected = buildExpectedSecuredAccountResponse();

        // When
        final SecuredAccountResponse result = converter.convert(buildAccount());

        // Then
        assertThat(result, notNullValue());
        assertThat(result, equalTo(expected));
    }

    private Account buildAccount() {
        return Account.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .username(USERNAME)
                .email(EMAIL)
                .passwordHash(PASSWORD_HASH)
                .authority(AUTHORITY_AS_STRING)
                .build();
    }

    private SecuredAccountResponse buildExpectedSecuredAccountResponse() {
        return SecuredAccountResponse.builder()
                .username(USERNAME)
                .password(PASSWORD_HASH)
                .authority(Authority.USER)
                .build();
    }
}
