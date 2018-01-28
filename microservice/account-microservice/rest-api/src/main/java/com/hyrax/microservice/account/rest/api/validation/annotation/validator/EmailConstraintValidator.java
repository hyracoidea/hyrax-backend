package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.Email;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailConstraintValidator implements ConstraintValidator<Email, String> {

    private Pattern pattern;

    @Override
    public void initialize(final Email email) {
        pattern = Pattern.compile(email.regex(), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(email) && pattern.matcher(email).matches();
    }
}
