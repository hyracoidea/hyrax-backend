package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.request.AccountRequest;
import com.hyrax.microservice.account.rest.api.validation.annotation.MatchesPassword;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(DataProviderRunner.class)
public class MatchesPasswordConstraintValidatorTest {

    private static final String PASSWORD_VALUE_WITH_UPPER_CASE = "PASSWORD";
    private static final String PASSWORD_VALUE_WITH_LOWER_CASE = "password";

    @Mock
    private MatchesPassword matchesPasswordMock;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private final MatchesPasswordConstraintValidator matchesPasswordConstraintValidator = new MatchesPasswordConstraintValidator();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @UseDataProvider("data")
    public void isValidWithDataProvider(final String baseField, final String baseFieldValue, final String matchField, final String matchFieldValue, final boolean expected) {
        // Given
        final AccountRequest accountRequest = AccountRequest.builder()
                .password(baseFieldValue)
                .passwordConfirmation(matchFieldValue)
                .build();

        prepareMatchesPasswordMock(baseField, matchField);

        matchesPasswordConstraintValidator.initialize(matchesPasswordMock);

        // When
        final boolean result = matchesPasswordConstraintValidator.isValid(accountRequest, constraintValidatorContext);

        // Then
        assertThat(result, is(expected));
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {"password", null, "passwordConfirmation", StringUtils.EMPTY, false},
                {"password", PASSWORD_VALUE_WITH_UPPER_CASE, "passwordConfirmation", null, false},
                {"password", PASSWORD_VALUE_WITH_UPPER_CASE, "passwordConfirmation", StringUtils.EMPTY, false},
                {"password", PASSWORD_VALUE_WITH_UPPER_CASE, "passwordConfirmation", PASSWORD_VALUE_WITH_LOWER_CASE, false},

                {"unknown", PASSWORD_VALUE_WITH_LOWER_CASE, "unknown", PASSWORD_VALUE_WITH_LOWER_CASE, false},

                {"password", null, "passwordConfirmation", null, true},
                {"password", StringUtils.EMPTY, "passwordConfirmation", StringUtils.EMPTY, true},
                {"password", PASSWORD_VALUE_WITH_LOWER_CASE, "passwordConfirmation", PASSWORD_VALUE_WITH_LOWER_CASE, true}

        };
    }

    private void prepareMatchesPasswordMock(final String baseField, final String matchField) {
        given(matchesPasswordMock.baseField()).willReturn(baseField);
        given(matchesPasswordMock.matchField()).willReturn(matchField);
    }
}
