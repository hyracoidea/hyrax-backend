package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.LastName;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameConstraintValidator implements ConstraintValidator<LastName, String> {

    private int minLength;

    private int maxLength;

    private String messageTemplate;

    @Override
    public void initialize(final LastName constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
        messageTemplate = String.format(constraintAnnotation.message(), minLength, maxLength);
    }

    @Override
    public boolean isValid(final String firstName, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
        return StringUtils.isNotEmpty(firstName) && isBetweenRange(StringUtils.trim(firstName), minLength, maxLength);
    }

    private boolean isBetweenRange(final String value, final int minLength, final int maxLength) {
        return minLength <= value.length() && value.length() <= maxLength;
    }
}
