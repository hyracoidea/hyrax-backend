package com.hyrax.microservice.account.rest.api.validation.annotation;

import com.hyrax.microservice.account.rest.api.validation.annotation.validator.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
public @interface Password {

    String regex() default "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,})";

    String message() default "Password must contain at least 1 number, 1 lowercase, 1 uppercase character and the length is min 6 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
