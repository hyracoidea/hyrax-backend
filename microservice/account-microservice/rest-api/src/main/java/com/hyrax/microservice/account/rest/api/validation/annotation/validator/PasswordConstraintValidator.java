package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.Password;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    private Pattern pattern;

    @Override
    public void initialize(final Password password) {
        pattern = Pattern.compile(password.regex());
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        return StringUtils.isNotEmpty(password) && pattern.matcher(password).matches();
    }
}
