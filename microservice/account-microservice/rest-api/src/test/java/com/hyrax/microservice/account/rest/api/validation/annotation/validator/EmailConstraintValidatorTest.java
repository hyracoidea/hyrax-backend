package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.Email;
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
public class EmailConstraintValidatorTest {

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    private final EmailConstraintValidator emailConstraintValidator = new EmailConstraintValidator();

    @Mock
    private Email emailMock;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        given(emailMock.regex()).willReturn(EMAIL_REGEX);
    }

    @Test
    @UseDataProvider("data")
    public void isValidWithDataProvider(final String email, final boolean expected) {
        // Given
        emailConstraintValidator.initialize(emailMock);

        // When
        final boolean result = emailConstraintValidator.isValid(email, constraintValidatorContext);

        // Then
        assertThat(result, is(expected));
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {null, false},
                {StringUtils.EMPTY, false},
                {"hyrax", false}, //  No @ symbole
                {"hyrax@.com.my", false}, // Dot after @ symbol
                {"hyrax123@gmail.a ", false}, //  last TLD length is less than 2
                {"hyrax123@@.com.com", false}, // Two @ symbols
                {".hyrax@hyrax.com", false}, // ID can’t start with .
                {"hyrax()*@gmail.com", false}, // invalid special characters in the ID
                {"hyrax@%*.com", false}, // invalid special characters in the TLD
                {"hyrax..2002@gmail.com", false}, // ID can’t have two dots
                {"hyrax.@gmail.com", false}, // ID can’t end with dot
                {"hyrax@hyrax@gmail.com", false}, // Two @ symbols
                {"hyrax@gmail.com.1a ", false}, // last TLD can have characters only


                {"hyrax@yahoo.com", true},
                {"hyrax-100@yahoo.com", true},
                {"hyrax.100@yahoo.com", true},
                {"hyrax111@hyrax.com", true},
                {"hyrax-100@hyrax.net", true},
                {"hyrax.100@hyrax.com.au", true},
                {"hyrax@1.com", true},
                {"hyrax@gmail.com.com", true},
                {"hyrax+100@gmail.com", true},
                {"hyrax-100@yahoo-test.com", true},
                {"hyrax_100@yahoo-test.ABC.CoM", true}

        };
    }
}
