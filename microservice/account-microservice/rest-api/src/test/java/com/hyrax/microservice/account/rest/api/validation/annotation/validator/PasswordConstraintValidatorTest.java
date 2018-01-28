package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.Password;
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
public class PasswordConstraintValidatorTest {

    private static final String CHAR_LOWERCASE = "a";
    private static final String CHAR_UPPERCASE = "A";
    private static final String NUMBER = "5";

    private static final String PASSWORD_REGEX = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,})";

    private final PasswordConstraintValidator passwordConstraintValidator = new PasswordConstraintValidator();

    @Mock
    private Password passwordMock;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        given(passwordMock.regex()).willReturn(PASSWORD_REGEX);
    }

    @Test
    @UseDataProvider("data")
    public void isValidWithDataProvider(final String password, final boolean expected) {
        // Given
        passwordConstraintValidator.initialize(passwordMock);

        // When
        final boolean result = passwordConstraintValidator.isValid(password, constraintValidatorContext);

        // Then
        assertThat(result, is(expected));
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {null, false},
                {StringUtils.EMPTY, false},
                {StringUtils.repeat(StringUtils.CR, 6), false},

                {CHAR_LOWERCASE, false},
                {StringUtils.repeat(CHAR_LOWERCASE, 6), false},

                {CHAR_UPPERCASE, false},
                {StringUtils.repeat(CHAR_UPPERCASE, 6), false},

                {NUMBER, false},
                {StringUtils.repeat(NUMBER, 6), false},

                {StringUtils.join(StringUtils.repeat(CHAR_LOWERCASE, 3), StringUtils.repeat(CHAR_UPPERCASE, 3)), false},
                {StringUtils.join(StringUtils.repeat(CHAR_LOWERCASE, 3), StringUtils.repeat(NUMBER, 3)), false},
                {StringUtils.join(StringUtils.repeat(CHAR_UPPERCASE, 3), StringUtils.repeat(NUMBER, 3)), false},

                {StringUtils.join(StringUtils.repeat(CHAR_LOWERCASE, 1), StringUtils.repeat(CHAR_UPPERCASE, 2), StringUtils.repeat(NUMBER, 2)), false},
                {StringUtils.join(StringUtils.repeat(CHAR_UPPERCASE, 1), StringUtils.repeat(NUMBER, 2), StringUtils.repeat(CHAR_LOWERCASE, 2)), false},
                {StringUtils.join(StringUtils.repeat(NUMBER, 1), StringUtils.repeat(CHAR_LOWERCASE, 2), StringUtils.repeat(CHAR_UPPERCASE, 2)), false},


                {StringUtils.join(StringUtils.repeat(CHAR_LOWERCASE, 1), StringUtils.repeat(CHAR_UPPERCASE, 1), StringUtils.repeat(NUMBER, 4)), true},
                {StringUtils.join(StringUtils.repeat(CHAR_UPPERCASE, 1), StringUtils.repeat(NUMBER, 1), StringUtils.repeat(CHAR_LOWERCASE, 4)), true},
                {StringUtils.join(StringUtils.repeat(NUMBER, 1), StringUtils.repeat(CHAR_LOWERCASE, 1), StringUtils.repeat(CHAR_UPPERCASE, 4)), true},

                {StringUtils.join(StringUtils.repeat(CHAR_LOWERCASE, 2), StringUtils.repeat(CHAR_UPPERCASE, 2), StringUtils.repeat(NUMBER, 2)), true},
                {StringUtils.join(StringUtils.repeat(CHAR_UPPERCASE, 2), StringUtils.repeat(NUMBER, 2), StringUtils.repeat(CHAR_LOWERCASE, 2)), true},
                {StringUtils.join(StringUtils.repeat(NUMBER, 2), StringUtils.repeat(CHAR_LOWERCASE, 2), StringUtils.repeat(CHAR_UPPERCASE, 2)), true},

                {StringUtils.join(StringUtils.repeat(CHAR_LOWERCASE, 10), StringUtils.repeat(CHAR_UPPERCASE, 5), StringUtils.repeat(NUMBER, 1)), true},
                {StringUtils.join(StringUtils.repeat(CHAR_UPPERCASE, 10), StringUtils.repeat(NUMBER, 5), StringUtils.repeat(CHAR_LOWERCASE, 1)), true},
                {StringUtils.join(StringUtils.repeat(NUMBER, 10), StringUtils.repeat(CHAR_LOWERCASE, 5), StringUtils.repeat(CHAR_UPPERCASE, 1)), true}
        };
    }
}
