package com.hyrax.microservice.project.rest.api.validation.annotation.validator;

import com.hyrax.microservice.project.rest.api.validation.annotation.TeamName;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TeamNameConstraintValidator implements ConstraintValidator<TeamName, String> {

    private int minLength;

    private int maxLength;

    private String messageTemplate;

    @Override
    public void initialize(final TeamName constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        maxLength = constraintAnnotation.maxLength();
        messageTemplate = String.format(constraintAnnotation.message(), minLength, maxLength);
    }

    @Override
    public boolean isValid(final String teamName, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
        return StringUtils.isNotEmpty(teamName) && isBetweenRange(StringUtils.trim(teamName), minLength, maxLength);
    }

    private boolean isBetweenRange(final String value, final int minLength, final int maxLength) {
        return minLength <= value.length() && value.length() <= maxLength;
    }
}
