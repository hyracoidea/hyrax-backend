package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.Username;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintValidator implements ConstraintValidator<Username, String> {

    private int minLength;

    private int maxLength;

    private String messageTemplate;

    @Override
    public void initialize(final Username constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
        messageTemplate = String.format(constraintAnnotation.message(), minLength, maxLength);
    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
        return StringUtils.isNotEmpty(username) && isBetweenRange(StringUtils.trim(username), minLength, maxLength);
    }

    private boolean isBetweenRange(final String value, final int minLength, final int maxLength) {
        return minLength <= value.length() && value.length() <= maxLength;
    }
}
