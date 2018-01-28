package com.hyrax.microservice.account.rest.api.validation.annotation.validator;

import com.hyrax.microservice.account.rest.api.validation.annotation.MatchesPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class MatchesPasswordConstraintValidator implements ConstraintValidator<MatchesPassword, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchesPasswordConstraintValidator.class);

    private String baseField;

    private String matchField;

    @Override
    public void initialize(final MatchesPassword matchesPassword) {
        this.baseField = matchesPassword.baseField();
        this.matchField = matchesPassword.matchField();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Class<?> classType = value.getClass();
            final Object fieldValue = getFieldValueByName(classType, value, baseField);
            final Object confirmationFieldValue = getFieldValueByName(classType, value, matchField);

            return Objects.equals(fieldValue, confirmationFieldValue);

        } catch (final NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("The [{}] field must be existing", e.getMessage(), e);
            return false;
        }
    }

    private Object getFieldValueByName(final Class<?> classType, final Object value, final String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = classType.getDeclaredField(fieldName);
        field.setAccessible(true);

        return field.get(value);
    }

}
