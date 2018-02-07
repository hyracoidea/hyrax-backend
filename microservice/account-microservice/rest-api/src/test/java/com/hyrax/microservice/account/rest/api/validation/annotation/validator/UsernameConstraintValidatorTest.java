package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.Username;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;

@RunWith(DataProviderRunner.class)
public class UsernameConstraintValidatorTest {

    private static final String MESSAGE_TEMPLATE = "Username should contain at least %d and at most %d characters";
    private static final String TEST_CHARACTER = "A";

    @Mock
    private Username usernameMock;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    private final UsernameConstraintValidator usernameConstraintValidator = new UsernameConstraintValidator();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @UseDataProvider("data")
    public void isValidWithDataProvider(final int minLength, final int maxLength, final String username, final boolean expected) {
        // Given
        prepareConstraintValidatorContext();
        prepareUsernameMock(minLength, maxLength, MESSAGE_TEMPLATE);

        usernameConstraintValidator.initialize(usernameMock);

        // When
        final boolean result = usernameConstraintValidator.isValid(username, constraintValidatorContext);

        // Then
        assertThat(result, is(expected));
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {2, 100, null, false},
                {2, 100, StringUtils.EMPTY, false},
                {2, 100, createStringBetweenSpecialCharacters(TEST_CHARACTER, StringUtils.LF), false},
                {2, 100, StringUtils.repeat(StringUtils.CR, 10), false},

                {2, 100, StringUtils.repeat(TEST_CHARACTER, 1), false},
                {2, 100, StringUtils.repeat(TEST_CHARACTER, 101), false},

                {2, 100, StringUtils.repeat(TEST_CHARACTER, 2), true},
                {2, 100, StringUtils.repeat(TEST_CHARACTER, 50), true},
                {2, 100, StringUtils.repeat(TEST_CHARACTER, 100), true},

        };
    }

    private static String createStringBetweenSpecialCharacters(final String value, final String specialCharacter) {
        return new StringBuilder().append(specialCharacter).append(value).append(specialCharacter).toString();
    }

    private void prepareConstraintValidatorContext() {
        doNothing().when(constraintValidatorContext).disableDefaultConstraintViolation();
        given(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString())).willReturn(constraintViolationBuilder);
        given(constraintViolationBuilder.addConstraintViolation()).willReturn(constraintValidatorContext);
    }

    private void prepareUsernameMock(final int minLength, final int maxLength, final String message) {
        given(usernameMock.minLength()).willReturn(minLength);
        given(usernameMock.maxLength()).willReturn(maxLength);
        given(usernameMock.message()).willReturn(message);
    }
}
